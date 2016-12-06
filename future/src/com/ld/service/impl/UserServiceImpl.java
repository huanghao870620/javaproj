package com.ld.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ld.dto.UserDto;
import com.ld.mapper.CfileMapper;
import com.ld.mapper.RoleMapper;
//import com.ld.mapper.CustomerUserMapper;
import com.ld.mapper.UserMapper;
import com.ld.mapper.UserRoleMapper;
import com.ld.model.Cfile;
import com.ld.model.MessageBO;
import com.ld.model.Role;
import com.ld.model.User;
import com.ld.model.UserRole;
import com.ld.model.ViewpointSpecialFileRela;
import com.ld.service.UserRoleService;
import com.ld.service.UserService;
import com.ld.sftp.SftpServ;
import com.ld.sftp.SftpServ.UploadStatus;
import com.ld.util.EncryptionTool;
import com.ld.util.Logs;
import com.opensymphony.xwork2.ActionContext;

@Transactional
@Service
public class UserServiceImpl extends BaseServiceImpl<User, UserMapper>
		implements UserService<User> {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private UserRoleService<UserRole> userRoleService;
	
	@Autowired(required = true)
	private SftpServ sftp;
	
	@Autowired
	private CfileMapper cfileMapper;
	/**
	 * 判断用户是否存在
	 * @return
	 */
	public boolean deteWhetheUserExists(UserDto user){
		 User retUser = this.findUser(user);
		 return null != retUser;
	}
	
	/**
	 * 获取用户
	 * @param user
	 * @return
	 */
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public User findUser(UserDto user){
		 user.setPassword(EncryptionTool.addSaltEncrypt(user.getPassword(), user.getUserName()));
		 return this.userMapper.findUserByNameAndPass(user);
	}
	
	/**
	 * 根据用户名获取用户
	 * @param user
	 * @return
	 */
	public User findUserByUserName(UserDto user){
		return this.userMapper.findUser(user);
	}
	
	/**
	 *添加用户
	 */
	public MessageBO addUser(UserDto dto){
		MessageBO messageBO = null;
		try {
			//验证用户表单数据
			boolean result = validateUserInfo(dto);
			if (result) {
				ActionContext ctx = ActionContext.getContext();
				Map<String, Object> maps = ctx.getParameters();
				String[] roles = (String[]) maps.get("role");
				Arrays.sort(roles);
				if(ArrayUtils.isEmpty(roles)){
					messageBO = new MessageBO("0","新增用户失败,未选择角色!");
					return messageBO;
				}
				//验证用户名是否存在
				User userResult = findUserByUserName(dto);
				if(userResult!=null){
					messageBO = new MessageBO("0","新增用户失败,该用户名已存在!");
					return messageBO;
				}
				User user = new User();
				user.setName(dto.getName());
				user.setUserName(dto.getUserName());
				user.setPassword(EncryptionTool.addSaltEncrypt(dto.getPassword(), dto.getUserName()));
				user.setRegistTime(new Date());
				user.setUpdateTime(new Date());
				user.setEmail(dto.getEmail());
				user.setCFileId(dto.getCFileId());
				super.insert(user);

				List<UserRole> userRoleList = new ArrayList<UserRole>();
				for (int i = 0; i < roles.length; i++) {
					UserRole ur = new UserRole();
					ur.setRoleid(new BigDecimal(roles[i]));
					ur.setUserid(user.getUserId());
					userRoleList.add(ur);
					if(roles[i].trim().equals("3")){//助理秘书
					user.setLevelId(new BigDecimal("10") );
					}else if(roles[i].trim().equals("4")){ //超级管理员
						user.setLevelId(new BigDecimal("11") );
					}else if(roles[i].trim().equals("1")){//讲师
						user.setLevelId(new BigDecimal("12") );
					}else if(roles[i].trim().equals("2")){//讲师助理
						user.setLevelId(new BigDecimal("13") );
					}
				}
				this.userMapper.updateByPrimaryKey(user);
				this.userRoleService.addUserRole(userRoleList);
				messageBO = new MessageBO("-1","新增用户成功!");
			} else {
				Logs.getLogger().info("UserServiceImpl.addUser 参数验证失败");
				messageBO = new MessageBO("0","新增用户失败,请填写完整的信息!");
			} 
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().info("UserServiceImpl.addUser", e);
			messageBO = new MessageBO("0","新增用户失败!");
		}
		return messageBO;
	}

	
	/**
	 * 删除
	 */
	public MessageBO delete(){
		MessageBO messageBO = null;
		try {
			ActionContext ctx = ActionContext.getContext();
			Map<String, Object> params = ctx.getParameters();
			String[] ids = (String[]) params.get("id");
			if(ArrayUtils.isEmpty(ids)){
				messageBO = new MessageBO("0","删除用户失败,请稍后再试!");
				return messageBO;
			}else{
				String idString = new String(ids[0]);
				String[] idList =idString.split(",");
				for(int k=0 ; k<idList.length ; k++){
					BigDecimal id = new BigDecimal(idList[k]);
			//	BigDecimal id = new BigDecimal(ids[0]);
				//删除用户角色关联关系
				this.userRoleMapper.delete(id);
				
				User user = userMapper.findById(id);
				
					Cfile cfile = cfileMapper.findById(user.getCFileId());
					//删除图片
					sftp.delete(cfile.getName());
					//删除文件表
					cfileMapper.delete(user.getCFileId());
				
					//删除用户
					super.delete(id);
				
			}
				messageBO = new MessageBO("-1","删除用户成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("UserServiceImpl.delete error",e);
			messageBO = new MessageBO("0","删除用户失败!");
		}
		return messageBO;
	}

	
	/**
	 *新增用户表单数据验证
	 * @param dto
	 * @return
	 */
	private boolean validateUserInfo(UserDto dto){
		//账户
		if(StringUtils.isBlank(dto.getUserName())){
			return false;
		}
		//姓名
		if(StringUtils.isBlank(dto.getName())){
			return false;
		}
		//密码
		if(StringUtils.isBlank(dto.getPassword())){
			return false;
		}
		//确认密码
		if(StringUtils.isBlank(dto.getDupPassword())){
			return false;
		}
		//密码和确认密码不同
		if(!dto.getPassword().equals(dto.getDupPassword())){
			return false;
		}
		//email
		if(StringUtils.isBlank(dto.getEmail())){
			return false;
		}
		return true;
	}

	/**
	 *  获取用户列表
	 */
	public List<User> queryUserListByAjax() {
		List<User> list = new ArrayList<User>();
		try {
			UserDto userDto = new UserDto();
			ActionContext ctx = ActionContext.getContext();
			Map<String, Object> params = ctx.getParameters();
			String[] userName = (String[]) params.get("userName");
			if(ArrayUtils.isNotEmpty(userName)&&StringUtils.isNotBlank(userName[0])){
				userDto.setUserName(userName[0]);
			}
			list = this.userMapper.findAllByPaging(userDto);
			System.out.println(list.size());
			for(int i=0;i<list.size();i++){
				User user =(User)list.get(i);
				List<UserRole> listUserRole =userRoleMapper.captureRoleByUserId(user.getUserId());
				StringBuffer roleNameStr=new StringBuffer();
				for(int j=0;j<listUserRole.size();j++){
					UserRole userRole = listUserRole.get(j);
				Role role =roleMapper.findById(userRole.getRoleid());
					roleNameStr.append(role.getRolename()+" ");
				}
				user.setRoleName(roleNameStr.toString());
			}
			Logs.getLogger().info("获取用户列表成功!");
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("UserServiceImpl.queryUserListByAjax error",e);
		}
		return list;
	}

	/**
	 * 根据ID获取用户信息
	 */
	public MessageBO findUserById() {
		MessageBO messageBO = null;
		try {
			ActionContext ctx = ActionContext.getContext();
			Map<String, Object> params = ctx.getParameters();
			String[] ids = (String[]) params.get("id");
			if(ArrayUtils.isEmpty(ids)){
				messageBO = new MessageBO("0","获取用户信息失败!");
				return messageBO;
			}else{
				User user = this.userMapper.findById(new BigDecimal(ids[0]));
				UserDto userDto = new UserDto();
				userDto.setUserId(user.getUserId());
				userDto.setUserName(user.getUserName());
				userDto.setName(user.getName());
				userDto.setEmail(user.getEmail());
				userDto.setCFileId(user.getCFileId());
				List<UserRole>  urList = userRoleMapper.captureRoleByUserId(user.getUserId());
				userDto.setRoleList(urList);
				messageBO = new MessageBO("-1","获取用户信息成功!",userDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("UserServiceImpl.findUserById error",e);
			messageBO = new MessageBO("0","获取用户信息失败!");
		}
		return messageBO;
	}

	/**
	 * 更新用户信息
	 */
	public MessageBO updateUser(UserDto userDto) {
		MessageBO messageBO = null;
		try {
			if(StringUtils.isBlank(userDto.getName())){
				messageBO = new MessageBO("0","更新用户信息失败!");
				Logs.getLogger().info("更新用户信息失败,参数不合法!");
				return messageBO;
			}
			ActionContext ctx = ActionContext.getContext();
			Map<String, Object> maps = ctx.getParameters();
			String[] roles = (String[]) maps.get("role");
			if(ArrayUtils.isEmpty(roles)){
				messageBO = new MessageBO("0","新增用户失败,未选择角色!");
				return messageBO;
			}
			//更新用户基本信息
			User user = this.userMapper.findById(userDto.getUserId());
			user.setName(userDto.getName());
			user.setEmail(userDto.getEmail());
			user.setUpdateTime(new Date());
			user.setCFileId(userDto.getCFileId());
			this.userMapper.updateByPrimaryKey(user);
			//删除原角色用户关联关系
			this.userRoleMapper.delete(user.getUserId());
			//重新建立角色用户关联关系
			List<UserRole> userRoleList = new ArrayList<UserRole>();
			for (int i = 0; i < roles.length; i++) {
				UserRole ur = new UserRole();
				ur.setRoleid(new BigDecimal(roles[i]));
				ur.setUserid(user.getUserId());
				userRoleList.add(ur);
				if(roles[i].trim().equals("3")){//助理秘书
					user.setLevelId(new BigDecimal("10") );
					}else if(roles[i].trim().equals("4")){ //超级管理员
						user.setLevelId(new BigDecimal("11") );
					}else if(roles[i].trim().equals("1")){//讲师
						user.setLevelId(new BigDecimal("12") );
					}else if(roles[i].trim().equals("2")){//讲师助理
						user.setLevelId(new BigDecimal("13") );
					}
			}
			this.userMapper.updateByPrimaryKey(user);
			this.userRoleService.addUserRole(userRoleList);
			messageBO = new MessageBO("-1","更新用户信息成功!");
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("UserServiceImpl.updateUser error",e);
			messageBO = new MessageBO("0","更新用户信息失败!");
		}
		return messageBO;
	}
	
	/**
	 * 修改用户密码
	 * @param userDto
	 * @return
	 */
	public MessageBO updateUserPass(UserDto userDto){
		MessageBO messageBO = null;
		try {
			if(StringUtils.isBlank(userDto.getPassword())){
				messageBO = new MessageBO("0","密码不能为空!");
				return messageBO;
			}else{
				User user = this.userMapper.findById(userDto.getUserId());
				user.setPassword(EncryptionTool.addSaltEncrypt(userDto.getPassword(), user.getUserName()));
				user.setUpdateTime(new Date());
				this.userMapper.updateByPrimaryKey(user);
				messageBO = new MessageBO("-1","密码修改成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logs.getLogger().error("UserServiceImpl.updateUserPass error",e);
			messageBO = new MessageBO("0","修改密码失败!");
		}
		return messageBO;
	}
	/**
	 * 上传用户头像
	 * 
	 * @throws IOException
	 */
	@Override
	public MessageBO uploadUserHeadPortrait(File file, String fileName) {
		
			//返回结果对象
			MessageBO messageBO = null;
			try {
				//解决不同的浏览器取到的文件类型不一样的问题
				List<String> types = new ArrayList<String>();
				types.add("gif");
				types.add("jpg");
				types.add("jpeg");
				types.add("png");
				if (fileName != null && fileName.trim() != "" 
					&& fileName.contains(".") 
					&& fileName.trim().split("\\.").length == 2 
					&& types.contains(fileName.split("\\.")[1].toLowerCase())) {
					//限制文件大小上限为3M
					long fileSize = file.length();
					if(fileSize > 3*1024*1024){
						messageBO = new MessageBO("0","文件大小不能超过3M，请重新选择文件上传!");
						return messageBO;
					}
					//文件后缀名
					String suffix = fileName.split("\\.")[1];
					// 时间戳
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
					String timestr = sdf.format(new Date());
					//  文件名
					String realName = timestr + "." + suffix;
					//文件存放物理路径
					HttpServletRequest request = ServletActionContext.getRequest();
					//临时文件路径
					String realPath = request.getSession().getServletContext().getRealPath("/temp");
					InputStream inputStream = new FileInputStream(file);
					BufferedImage sourceImg = ImageIO.read(inputStream);
					int width = sourceImg.getWidth();
					if(width>945){
						messageBO = new MessageBO("0","请上传规定尺寸的图片!");
						return messageBO;
					}
					FileUtils.copyInputStreamToFile(new FileInputStream(file), new File(realPath, fileName));
					//SFTP上传文件
				//	UploadStatus uploadStatus = ftp.upload(realPath + File.separator + fileName, "Send/" + realName);
				
						UploadStatus uploadStatus = sftp.upload(realPath + File.separator + fileName, realName);
					if(uploadStatus != UploadStatus.Upload_New_File_Success){
						messageBO = new MessageBO("0","上传图片失败!");
						return messageBO;
					}
					//保存文件信息
					Cfile cfile = new Cfile();
					cfile.setName(realName);
					cfile.setType(suffix);
					cfile.setUrlCode(realPath);
					cfile.setFilesize(new BigDecimal(file.length()));
					cfile.setCreateTime(new Date());
					cfile.setOriginalName(fileName);
					cfileMapper.insert(cfile);

					//清除临时目录
					file = new File(realPath , fileName);
					if (file.isFile() && file.exists()) {
						file.delete();
					}
					//返回文件相对应ID，用于文件下载
					messageBO = new MessageBO("-1","上传图片成功!",cfile.getId());
				} else {
					messageBO = new MessageBO("0","上传的文件格式不正确,请重新上传!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				Logs.getLogger().error("UserServiceImpl.uploadUserHeadPortrait error",e);
				messageBO = new MessageBO("0","上传的文件格式不正确,请重新上传!");
			}
			return messageBO;
		}
}
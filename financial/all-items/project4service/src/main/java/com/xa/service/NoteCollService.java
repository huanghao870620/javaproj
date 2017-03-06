package com.xa.service;

import com.xa.entity.NoteColl;

public interface NoteCollService<T> extends BaseServiceInte<T> {


	public String addNoteColl(NoteColl noteColl,String sign);
}

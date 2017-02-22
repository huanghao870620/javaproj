package com.xa.mapper;

import com.xa.entity.NotePraise;

public interface NotePraiseMapper extends BaseMapper<NotePraise>{
	
	void deleteByNoteIdAndBuyerId(NotePraise notePraise);
	
	Long getPraiseCountByNoteId(Long noteId);
}
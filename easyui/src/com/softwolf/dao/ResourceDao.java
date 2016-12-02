package com.softwolf.dao;

import java.util.List;

import com.softwolf.dto.TreeDTO;

public interface ResourceDao {

	List<TreeDTO> getChildByParentId(String id);


}

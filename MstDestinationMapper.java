package jp.co.internous.printemps.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import jp.co.internous.printemps.model.domain.MstDestination;

@Mapper
public interface MstDestinationMapper {
	
	@Select("SELECT * FROM mst_destination WHERE user_id = #{userId} AND status = 1 ORDER BY id ASC")
	List<MstDestination> findDestinationByUserId(@Param("userId")int userId);
}

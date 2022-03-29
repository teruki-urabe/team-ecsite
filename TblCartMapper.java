package jp.co.internous.printemps.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import jp.co.internous.printemps.model.domain.TblCart;
import jp.co.internous.printemps.model.domain.dto.CartDto;

@Mapper
public interface TblCartMapper {
	List<CartDto>findCartByUserId(@Param("userId")int userId);

	@Insert("INSERT INTO tbl_cart(user_id,product_id,product_count)" + 
			"VALUES (#{userId},#{productId},#{productCount})")
	@Options(useGeneratedKeys=true, keyProperty="id")
	void insertCart(TblCart tblCart);

	@Delete("DELETE FROM tbl_cart WHERE id = #{id}")
	int deleteCartById(@Param("id") int id);

	@Update("UPDATE tbl_cart SET product_count = product_count + #{productCount},updated_at = now() WHERE product_id = #{productId} and user_id = #{userId}")
	void updateProductCountByUserIdAndProductId(TblCart cart);

	@Delete("DELETE FROM tbl_cart WHERE user_id = #{userId}")
	void deleteCartByUserId(int userId);

	@Select("SELECT COUNT(user_id) FROM tbl_cart WHERE user_id = #{tmpUserId}")
	int findCountByUserId(int tmpUserId);

	@Update("UPDATE tbl_cart SET user_id = #{userId}, updated_at = now() WHERE user_id = #{tmpUserId}")
	int updateUserId(@Param("userId") int userId, @Param("tmpUserId") int tmpUserId);

	@Select("SELECT count(id) FROM tbl_cart WHERE user_id = #{userId} AND product_id = #{productId}")
	int findCountByUserIdAndProuductId(@Param("userId") int userId, @Param("productId") int productId);
}

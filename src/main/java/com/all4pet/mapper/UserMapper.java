package com.all4pet.mapper;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.all4pet.entity.UserProfileEntity;
import com.all4pet.entity.RoleUserEntity;
import com.all4pet.entity.UserEntity;

@Mapper
public interface UserMapper {
	public void insertUser(UserEntity userEntity);
    public void deleteUser(long id);
    public UserEntity getUserByUserNameOrEmail(@Param ("userName") String userName, @Param ("email") String email);
    public List<UserEntity> selectAllUser();
	public RoleUserEntity getRole(long roleid);
	public UserEntity getUserByUserName (String userName);
	public UserEntity autoLogin (String userName,String password);
	public void insertUserProfile(UserProfileEntity userProfile);
	public UserProfileEntity getUserProfile(Long userId);
	public void updateUserProfileByUserId(long userId, String name, String address, String phoneNumber,
			Date birthdayTypeDate);
	public void changePasswordByUserId(Long userId, String newPassword);
	public void updateUserByUserName(UserEntity user);
	public List<UserEntity> paging10User(int offset);
	public void deleteUserProfile(long id);
	public void deleteUserCart(long id);
	public UserEntity getUserById(long userId);
}
/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xuhai.telescopes;

import com.xuhai.easeui.EaseConstant;

public class Constant extends EaseConstant {
	public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
	public static final String GROUP_USERNAME = "item_groups";
	public static final String CHAT_ROOM = "item_chatroom";
	public static final String ACCOUNT_REMOVED = "account_removed";
	public static final String ACCOUNT_CONFLICT = "conflict";
	public static final String CHAT_ROBOT = "item_robots";
	public static final String MESSAGE_ATTR_ROBOT_MSGTYPE = "msgtype";
	public static final String ACTION_GROUP_CHANAGED = "action_group_changed";
	public static final String ACTION_CONTACT_CHANAGED = "action_contact_changed";

	//服务器地址
	public static final String sever_host_url="http://112.74.115.90:8082/";

	//登录地址
	public static final String login_url=sever_host_url+"api/v1/sessions";
	//注销
	public static final String logout_url=sever_host_url+"api/v1/sessions";
	//用户注册
	public static final String register_url =sever_host_url+"api/v1/users";
	//获得某个用户的信息
	public static final String getOneUserInfomations_url=sever_host_url+"api/v1/users";
	//修改用户信息
	public static final String modifyUsersInfomations_url=sever_host_url+"api/v1/users/current_user";


	//获得所有分组及用户
	public static final String getGroupsAndUsers_url=sever_host_url+"api/v1/groups";
	//获得分组信息
	public static final String getGroupsInfomations_url=sever_host_url+"api/v1/groups/:";
	//修改分组信息
	public static final String setGroupsInfomations_url=sever_host_url+"api/v1/groups/:";
	//删除分组
	public static final String deleteGroups_url=sever_host_url+"api/v1/groups/";


	//添加好友
	public static final String addFriends_url=sever_host_url+"api/v1/friends";
	//删除单个好友
	public static final String deleteFriends_url=sever_host_url+"api/v1/friends/users/:";
	//删除多个好友
	public static final String deleteSomeFriends_url=sever_host_url+"ap1/v1/friends/users";
	//接受对方好友申请
	public static final String acceptFriendsApplication_url=sever_host_url+"api/v1/friends/:friendship_id/confirmed";
	//加入黑名单
	public static final String moveToBlacklist_url=sever_host_url+"api/v1/friends/blocks/users";
	//移出黑名单
	public static final String moveOutFromBlacklist_url=sever_host_url+"api/v1/friends/blocks/users";
	//黑名单列表
	public static final String blacklist_url=sever_host_url+"api/v1/friends/blocks/users";



}

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
	public static final String serve_host_url ="http://192.168.0.108:3000/";//"http://112.74.115.90:8082/";

	//登录地址
	public static final String login_url= serve_host_url +"api/v1/sessions";
	//注销
	public static final String logout_url= serve_host_url +"api/v1/sessions";
	//用户注册
	public static final String register_url = serve_host_url +"api/v1/users";
	//获得某个用户的信息
	public static final String getOneUserInfomations_url= serve_host_url +"api/v1/users";
	//修改用户信息
	public static final String modifyUsersInfomations_url= serve_host_url +"api/v1/users/current_user";



	private static String groud = serve_host_url +"api/v1/groups";
	//获得所有分组及用户
	public static final String getGroupsAndUsers_url= groud;
	//获得分组信息
	public static final String getGroupsInfomations_url= groud;
	//修改分组信息
	public static final String setGroupsInfomations_url= groud;
	//创建分组
	public static final String createAnGroup_url = groud;
	//删除分组
	public static final String deleteGroups_url= groud;
	//组成员添加
	public static final String addUserToGroup = groud;



	private static String friends = serve_host_url +"api/v1/friends";
	//添加好友
	public static final String addFriends_url= friends;
	//删除单个好友
	public static final String deleteFriends_url= friends;
	//删除多个好友
	public static final String deleteSomeFriends_url= friends;
	//接受对方好友申请
	public static final String acceptFriendsApplication_url= friends;



	private static String blacklist = serve_host_url +"api/v1/friends/blocks/users";
	//加入黑名单
	public static final String moveToBlacklist_url= blacklist;
	//移出黑名单
	public static final String moveOutFromBlacklist_url= blacklist;
	//黑名单列表
	public static final String blacklist_url= blacklist;



	private static String ally = serve_host_url +"api/v1/allies";
	//获得所有群组
	public static final String getAllAlliesFromServe_url = ally;
	//创建群
	public static final String createAnAlly_url = ally;
	//获取群详细信息
	public static final String getAnAllyInfomations_url = ally;
	//修改群组信息
	public static final String modifyAnAllyInfomations_url = ally;
	//邀请加入群
	public static final String inviteToAnAlly_url = ally;
	//确认加群
	public static final String conFirmToAnAlly_url = ally;
	//退出群
	public static final String quitFromAnAlly_url = ally;

	/**大海接口**/
	//获取大海所有评论的用户名单
	public static final String getOceanEffectList = serve_host_url+"/api/v1/questions/:id/persons";//get
	//评出最有影响力的评论用户
	public static final String selectOceanEffectUser = serve_host_url+"/api/v1/questions/:id/users/:user_id/best_answer";//post
	//发布大海主题
	public static final String publishOceanTopic = serve_host_url+"/api/v1/questions"; //post
	//获取大海主题列表
	public static final String getOceanTopicList = serve_host_url+"/api/v1/questions"; //get
	//获取大海主题详情
	public static final String getOceanTopicDetail = serve_host_url+"api/v1/questions/";//get
	//删除大海主题
	public static final String deleteOceanTopicDetail = serve_host_url+"api/v1/questions/";//delete
	//结题
	public static final String completeOceanTopicDetail = serve_host_url;
	//获取大海主题的评论列表
	public static final String getOceanTopicComment = serve_host_url+"/api/v1/questions/:id/comments";//get
	//获取某用户对某主题的所有评论
	public static final String getOceanUserComment = serve_host_url+"api/v1/questions/:id/users/:user_id/comments";//get
	//获取某用户对某主题的信息链
	public static final String getOceanLink = serve_host_url+"api/v1/questions/:id/users/:user_id/inv_relative";//get
	//对大海主题发表评论
	public static final String addOceanTopicComment = serve_host_url+"/api/v1//questions/:id/comments";//post
	//获取用户名片
	public static final String getUserCard = serve_host_url;

}

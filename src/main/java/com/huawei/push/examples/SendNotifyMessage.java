/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.huawei.push.examples;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.huawei.push.android.AndroidNotification;
import com.huawei.push.android.BadgeNotification;
import com.huawei.push.android.Button;
import com.huawei.push.android.ClickAction;
import com.huawei.push.android.Color;
import com.huawei.push.android.LigthSettings;
import com.huawei.push.exception.HuaweiMesssagingException;
import com.huawei.push.message.AndroidConfig;
import com.huawei.push.message.Message;
import com.huawei.push.message.Notification;
import com.huawei.push.messaging.HuaweiApp;
import com.huawei.push.messaging.HuaweiMessaging;
import com.huawei.push.model.Importance;
import com.huawei.push.model.Urgency;
import com.huawei.push.model.Visibility;
import com.huawei.push.reponse.SendResponse;
import com.huawei.push.util.InitAppUtils;

public class SendNotifyMessage {
    /**
     * send notification message
     *
     * @throws HuaweiMesssagingException
     */
    public static SendResponse sendNotification() throws HuaweiMesssagingException {
        HuaweiApp app = InitAppUtils.initializeApp();
        HuaweiMessaging huaweiMessaging = HuaweiMessaging.getInstance(app);

        Notification notification = Notification.builder().setTitle("sample title")
                .setBody("sample message body")
                .build();

        JSONObject multiLangKey = new JSONObject();
        JSONObject titleKey = new JSONObject();
        titleKey.put("en","好友请求");
        JSONObject bodyKey = new JSONObject();
        titleKey.put("en","My name is %s, I am from %s.");
        multiLangKey.put("key1", titleKey);
        multiLangKey.put("key2", bodyKey);

        LigthSettings lightSettings = LigthSettings.builder().setColor(Color.builder().setAlpha(0f).setRed(0f).setBlue(1f).setGreen(1f).build())
                .setLightOnDuration("3.5")
                .setLightOffDuration("5S")
                .build();

        AndroidNotification androidNotification = AndroidNotification.builder()
                /**
                 * 安卓通知栏消息标题，如果此处设置了title则会覆盖message.notification.title字段，且发送通知栏消息时，此处title和message.notification.title两者最少需要设置一个。
                 */
                .setTitle("title")
                /**
                 * 自定义通知栏左侧小图标，此处设置的图标文件必须存放在应用的/res/raw路径下，例如"/raw/ic_launcher"，对应应用本地的/res/raw/ic_launcher.xxx文件，支持的文件格式目前包括png、jpg。
                 */
                .setIcon("/raw/ic_launcher2")
                /**
                 * 自定义通知栏按钮颜色，以#RRGGBB格式，其中RR代表红色的16进制色素，GG代表绿色的16进制色素，BB代表蓝色的16进制色素，样例：#FFEEFF。
                 */
                .setColor("#FF0000")
                /**
                 * 自定义消息通知铃声，在新创建渠道时有效，此处设置的铃声文件必须存放在应用的/res/raw路径下，例如设置为"/raw/shake"，对应应用本地的/res/raw/shake.xxx文件，支持的文件格式包括mp3、wav、mpeg等，如果不设置使用默认系统铃声。
                 */
                .setSound("/raw/shake")
                /**
                 * 默认铃声控制开关，取值如下：
                 * true：代表使用系统默认铃声
                 * false：代表使用sound自定义铃声
                 */
                .setDefaultSound(true)
                /**
                 * 消息标签，同一应用下使用同一个消息标签的消息会相互覆盖，只展示最新的一条。
                 */
                .setTag("tagBoom")
                /**
                 * 消息点击行为
                 */
                .setClickAction(ClickAction.builder()
                        .setType(2)
                        .setUrl("https://www.huawei.com")
                        .build())
                /**
                 * 显示本地化body的StringId
                 */
                .setBodyLocKey("M.String.body")
                /**
                 * 本地化body的可变参数
                 */
                .addBodyLocArgs("boy").addBodyLocArgs("dog")
                /**
                 * 显示本地化title的StringId
                 */
                .setTitleLocKey("M.String.title")
                /**
                 * 本地化title的可变参数
                 */
                .addTitleLocArgs("Girl").addTitleLocArgs("Cat")
                /**
                 * 自Android O版本后可以支持通知栏自定义渠道，指定消息要展示在哪个通知渠道上
                 */
                .setChannelId("Your Channel ID")
                /**
                 * 安卓通知栏消息简要描述。
                 */
                .setNotifySummary("some summary")
                /**
                 * 消息国际化多语言参数，body_loc_key,   title_loc_key 优先从multi_lang_Key读取内容，如果key不存在，则从APK本地字符串资源读
                 */
                .setMultiLangkey(multiLangKey)
                /**
                 * 通知栏样式，取值如下：
                 * 0：默认样式
                 * 1：大文本样式
                 * 3：Inbox样式
                 */
                .setStyle(3)
                .addInboxContent("1、inbox Content 1").addInboxContent("2、inbox Content 2")
                /**
                 * 安卓通知栏消息大文本标题，当style为1时必选，设置big_title后通知栏展示时，使用big_title而不用title。
                 */
                .setBigTitle("Big Boom Title")
                /**
                 * 安卓通知栏消息大文本内容，当style为1时必选，设置big_body后通知栏展示时，使用big_body而不用body。
                 */
                .setBigBody("Big Boom Body")
                /**
                 * 消息展示时长，超过后自动清除，单位为毫秒。
                 */
                .setAutoClear(86400000)
                /**
                 * 每条消息在通知显示时的唯一标识。不携带时或者设置-1时，Push NC自动为给每条消息生成一个唯一标识；不同的通知栏消息可以相同的notifyId，实现新的通知栏消息覆盖老的。
                 */
                .setNotifyId(486)
                /**
                 * 消息分组，例如发送10条消息，group设置一样，手机上收到消息后在通知栏上不展示10条消息，而且将同一组的消息只显示1条，而不是10条。
                 */
                .setGroup("Group1")
                /**
                 * 安卓通知栏消息优先级，决定用户设备消息通知行为，取值如下：
                 * LOW：一般（静默）消息
                 * NORMAL：重要消息
                 * HIGH：非常重要消息
                 */
                .setImportance(Importance.LOW.getValue())
                /**
                 * 自定义呼吸灯模式
                 */
                .setLightSettings(lightSettings)
                /**
                 * 安卓通知消息角标控制
                 */
                .setBadge(BadgeNotification.builder()
                        .setAddNum(1)
                        .setBadgeClass("Classic").build())
                /**
                 * 安卓通知栏消息可见性，取值如下：
                 * VISIBILITY_UNSPECIFIED: 未指定visibility，效果等同于设置了PRIVATE
                 * PRIVATE: 锁屏时收到通知栏消息，显示消息内容。
                 * PUBLIC: 锁屏时收到通知栏消息，不提示收到通知消息。
                 * SECRET: 设置了锁屏密码，“锁屏通知”（导航：“设置”--“通知中心”）选择“隐藏通知内容”时收到通知消息，不显示消息内容。
                 */
                .setVisibility(Visibility.PUBLIC.getValue())
                /**
                 * 设备应用在前台时通知栏消息是否前台展示开关
                 */
                .setForegroundShow(true)
                .addButton(Button.builder()
                        .setName("button1")
                        .setActionType(0)
                        .build())
                .addButton(Button.builder()
                        .setName("button2")
                        .setActionType(2)
                        .setIntent("https://www.huawei.com")
                        .build())
                .addButton(Button.builder()
                        .setName("button3")
                        .setActionType(3).build())
                .build();

        AndroidConfig androidConfig = AndroidConfig.builder()
                /**
                 * 用户设备离线时，Push服务器对离线消息缓存机制的控制方式，用户设备上线后缓存消息会再次下发，取值如下：
                 * 0：对每个应用发送到该用户设备的离线消息只会缓存最新的一条；
                 * -1：对所有离线消息都缓存
                 * 1~100：离线消息缓存分组标识，对离线消息进行分组缓存，每个应用每一组最多缓存一条离线消息；
                 */
                .setCollapseKey(-1)
                /**
                 * 透传消息投递优先级，取值如下：
                 * HIGH
                 * NORMAL
                 * 设置HIGH时需要申请权限
                 * HIGH时透传消息到达用户手机时可强制拉起应用进程。
                 */
                .setUrgency(Urgency.HIGH.getValue())
                /**
                 * 消息缓存时间，单位是秒。
                 * 默认值为86400 （1天），最大值为15天。
                 */
                .setTtl("10000s")
                /**
                 * 批量任务消息标识，消息回执时会返回给应用服务器，应用服务器可以识别bi_tag对消息的下发情况进行统计分析。
                 */
                .setBiTag("the_sample_bi_tag_for_receipt_service")
                .setNotification(androidNotification)
                .build();

        Message message = Message.builder().setNotification(notification)
                .setAndroidConfig(androidConfig)
                //.addToken("AND8rUp4etqJvbakK7qQoCVgFHnROXzH8o7B8fTl9rMP5VRFN83zU3Nvmabm3xw7e3gZjyBbp_wfO1jP-UyDQcZN_CtjBpoa7nx1WaVFe_3mqXMJ6nXJNUZcDyO_-k3sSw")
                .addToken("AKHetDqajAJTiaUJM-J51bxaUxuX-6MWck2LMKZj_V1lBuOOSrIA27qu_CjorYWTOlawCjs9S_Y6IeFJBqXGqqKJ1KG5LYBzh54bLKaA8AS42U-d31xCpIZ7yAKLvsErXw")
                .build();

        System.out.println("message: " + JSON.toJSONString(message, SerializerFeature.WriteDateUseDateFormat));
        SendResponse response = huaweiMessaging.sendMessage(message);
        return response;
    }

    public static void main(String[] args) throws HuaweiMesssagingException {
        SendResponse response = sendNotification();
        System.out.println(JSON.toJSONString(response, SerializerFeature.WriteDateUseDateFormat));
    }
}

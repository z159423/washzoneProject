package com.WashZone.WashZone;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.friends.AppFriendContext;
import com.kakao.friends.AppFriendOrder;
import com.kakao.friends.response.AppFriendsResponse;
import com.kakao.friends.response.model.AppFriendInfo;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.response.KakaoTalkProfile;
import com.kakao.kakaotalk.v2.KakaoTalkService;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.TemplateParams;
import com.kakao.message.template.TextTemplate;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;

import java.util.ArrayList;
import java.util.List;

public class AdminmenuActivity extends AppCompatActivity {

    List<String> uuids = new ArrayList<>();

    Button btn_back, btn_testsms, btn_birthdayuserMenu, btn_alluser, btn_finduser, btn_smsSetting, btn_smsSetting2, btn_SMSRecord,btn_Setting, btn_SqlQuery, btn_kakaotest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminmenu);

        btn_back = findViewById(R.id.btn_back);
        btn_birthdayuserMenu = findViewById(R.id.btn_birthdayuser);
        //btn_alluser = findViewById(R.id.btn_alluser);
        btn_finduser = findViewById(R.id.btn_finduser);
        btn_smsSetting = findViewById(R.id.btn_SMSSetting);
        btn_SMSRecord = findViewById(R.id.btn_SMSRecord);
        btn_smsSetting2 = findViewById(R.id.btn_SMSSetting2);
        btn_Setting = findViewById(R.id.btn_Setting);
        btn_SqlQuery = findViewById(R.id.btn_SqlQuery);
        btn_kakaotest = findViewById(R.id.btn_kakaotest);

        new MyApplication();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminmenuActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

        btn_birthdayuserMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminmenuActivity.this,birthdayuserActivity.class);
                startActivity(intent);

            }
        });
/*
        btn_alluser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminmenuActivity.this,AllUserActivity.class);
                startActivity(intent);
            }
        });
*/
        btn_finduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminmenuActivity.this,FindUserActivity.class);
                startActivity(intent);
            }
        });

        btn_smsSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminmenuActivity.this,SmsSettingActivity.class);
                startActivity(intent);
            }
        });

        btn_smsSetting2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminmenuActivity.this,LostMentActivity.class);
                startActivity(intent);
            }
        });

        btn_SMSRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminmenuActivity.this,SMSRecordActivity.class);
                startActivity(intent);
            }
        });

        btn_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminmenuActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });


        btn_SqlQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminmenuActivity.this,SqlQueryActivity.class);
                startActivity(intent);
            }
        });

        btn_kakaotest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinkObject link = LinkObject.newBuilder()
                        .setWebUrl("https://developers.kakao.com")
                        .setMobileWebUrl("https://developers.kakao.com")
                        .build();
                TemplateParams params = TextTemplate.newBuilder("Text", link)
                        .setButtonTitle("This is button")
                        .build();


                UserManagement.getInstance()
                        .me(new MeV2ResponseCallback() {
                            @Override
                            public void onSessionClosed(ErrorResult errorResult) {
                                Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                            }

                            @Override
                            public void onFailure(ErrorResult errorResult) {
                                Log.e("KAKAO_API", "사용자 정보 요청 실패: " + errorResult);
                            }

                            @Override
                            public void onSuccess(MeV2Response result) {
                                Log.i("KAKAO_API", "사용자 아이디: " + result.getId());

                                UserAccount kakaoAccount = result.getKakaoAccount();
                                if (kakaoAccount != null) {

                                    // 이메일
                                    String email = kakaoAccount.getEmail();

                                    if (email != null) {
                                        Log.i("KAKAO_API", "email: " + email);

                                    } else if (kakaoAccount.emailNeedsAgreement() == OptionalBoolean.TRUE) {
                                        // 동의 요청 후 이메일 획득 가능
                                        // 단, 선택 동의로 설정되어 있다면 서비스 이용 시나리오 상에서 반드시 필요한 경우에만 요청해야 합니다.

                                    } else {
                                        // 이메일 획득 불가
                                    }

                                    // 프로필
                                    Profile profile = kakaoAccount.getProfile();

                                    if (profile != null) {
                                        Log.d("KAKAO_API", "nickname: " + profile.getNickname());
                                        Log.d("KAKAO_API", "profile image: " + profile.getProfileImageUrl());
                                        Log.d("KAKAO_API", "thumbnail image: " + profile.getThumbnailImageUrl());

                                    } else if (kakaoAccount.profileNeedsAgreement() == OptionalBoolean.TRUE) {
                                        // 동의 요청 후 프로필 정보 획득 가능

                                    } else {
                                        // 프로필 획득 불가
                                    }
                                }

                                KakaoTalkService.getInstance()
                                        .requestProfile(new TalkResponseCallback<KakaoTalkProfile>() {
                                            @Override
                                            public void onNotKakaoTalkUser() {
                                                Log.e("KAKAO_API", "카카오톡 사용자가 아님");
                                            }

                                            @Override
                                            public void onSessionClosed(ErrorResult errorResult) {
                                                Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                                            }

                                            @Override
                                            public void onFailure(ErrorResult errorResult) {
                                                Log.e("KAKAO_API", "카카오톡 프로필 조회 실패: " + errorResult);
                                            }

                                            @Override
                                            public void onSuccess(KakaoTalkProfile result) {
                                                Log.i("KAKAO_API", "카카오톡 닉네임: " + result.getNickName());
                                                Log.i("KAKAO_API", "카카오톡 프로필이미지: " + result.getProfileImageUrl());

                                                AppFriendContext context =
                                                        new AppFriendContext(AppFriendOrder.NICKNAME, 0, 100, "asc");


                                                KakaoTalkService.getInstance()
                                                        .requestAppFriends(context, new TalkResponseCallback<AppFriendsResponse>() {
                                                            @Override
                                                            public void onNotKakaoTalkUser() {
                                                                Log.e("KAKAO_API", "카카오톡 사용자가 아님");
                                                            }

                                                            @Override
                                                            public void onSessionClosed(ErrorResult errorResult) {
                                                                Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                                                            }

                                                            @Override
                                                            public void onFailure(ErrorResult errorResult) {
                                                                Log.e("KAKAO_API", "친구 조회 실패: " + errorResult);
                                                            }

                                                            @Override
                                                            public void onSuccess(AppFriendsResponse result) {
                                                                Log.i("KAKAO_API", "친구 조회 성공");

                                                                for (AppFriendInfo friend : result.getFriends()) {
                                                                    Log.d("KAKAO_API", friend.toString());

                                                                    Log.d("KAKAO_API", "친구 닉네임 : " + friend.getProfileNickname());

                                                                    String uuid = friend.getUUID();     // 메시지 전송 시 사용
                                                                    uuids.add(uuid);

                                                                }

                                                                if (context.hasNext()) {
                                                                    requestNextFriends(context);
                                                                }

                                                                Log.d("KAKAO_API", "uuids 정보 : " + uuids);


                                                                //*****************************************검수 받으면 모든 카카오톡 친구에게 문자가 갈수 있으므로 주의************************************
                                                                /*KakaoTalkService.getInstance()
                                                                        .sendMessageToFriends(uuids, params, new TalkResponseCallback<MessageSendResponse>() {
                                                                            @Override
                                                                            public void onNotKakaoTalkUser() {
                                                                                Log.e("KAKAO_API", "카카오톡 사용자가 아님");
                                                                            }

                                                                            @Override
                                                                            public void onSessionClosed(ErrorResult errorResult) {
                                                                                Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                                                                            }

                                                                            @Override
                                                                            public void onFailure(ErrorResult errorResult) {
                                                                                Log.e("KAKAO_API", "친구에게 보내기 실패: " + errorResult);
                                                                            }

                                                                            @Override
                                                                            public void onSuccess(MessageSendResponse result) {
                                                                                if (result.successfulReceiverUuids() != null) {
                                                                                    Log.i("KAKAO_API", "친구에게 보내기 성공");
                                                                                    Log.d("KAKAO_API", "전송에 성공한 대상: " + result.successfulReceiverUuids());
                                                                                }
                                                                                if (result.failureInfo() != null) {
                                                                                    Log.e("KAKAO_API", "일부 사용자에게 메시 보내기 실패");
                                                                                    for (MessageFailureInfo failureInfo : result.failureInfo()) {
                                                                                        Log.d("KAKAO_API", "code: " + failureInfo.code());
                                                                                        Log.d("KAKAO_API", "msg: " + failureInfo.msg());
                                                                                        Log.d("KAKAO_API", "failure_uuids: " + failureInfo.receiverUuids());
                                                                                    }
                                                                                }
                                                                            }
                                                                        });*/
                                                            }
                                                        });
                                            }
                                        });
                            }
                        });

                /*FeedTemplate params = FeedTemplate
                        .newBuilder(ContentObject.newBuilder("디저트 사진",
                                "http://mud-kage.kakao.co.kr/dn/NTmhS/btqfEUdFAUf/FjKzkZsnoeE4o19klTOVI1/openlink_640x640s.jpg",
                                LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")
                                        .setMobileWebUrl("https://developers.kakao.com").build())
                                .setDescrption("아메리카노, 빵, 케익")
                                .build())
                        .setSocial(SocialObject.newBuilder().setLikeCount(10).setCommentCount(20)
                                .setSharedCount(30).setViewCount(40).build())
                        .addButton(new ButtonObject("웹에서 보기", LinkObject.newBuilder().setWebUrl("'https://developers.kakao.com").setMobileWebUrl("'https://developers.kakao.com").build()))
                        .addButton(new ButtonObject("앱에서 보기", LinkObject.newBuilder()
                                .setWebUrl("'https://developers.kakao.com")
                                .setMobileWebUrl("'https://developers.kakao.com")
                                .setAndroidExecutionParams("key1=value1")
                                .setIosExecutionParams("key1=value1")
                                .build()))
                        .build();*/

                /*Map<String, String> serverCallbackArgs = new HashMap<String, String>();
                serverCallbackArgs.put("user_id", "${current_user_id}");
                serverCallbackArgs.put("product_id", "${shared_product_id}");

                KakaoLinkService.getInstance()
                        .sendDefault(MainActivity.this, params, new ResponseCallback<KakaoLinkResponse>() {
                            @Override
                            public void onFailure(ErrorResult errorResult) {
                                Log.e("KAKAO_API", "카카오링크 공유 실패: " + errorResult);
                            }

                            @Override
                            public void onSuccess(KakaoLinkResponse result) {
                                Log.i("KAKAO_API", "카카오링크 공유 성공");

                                // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                                Log.w("KAKAO_API", "warning messages: " + result.getWarningMsg());
                                Log.w("KAKAO_API", "argument messages: " + result.getArgumentMsg());
                            }
                        });*/
            }
        });
    }

    private void sendSMS(String phoneNumber, String message)
    {

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

    public void requestNextFriends(AppFriendContext context) {

        // 컨텍스트를 재사용하여 친구 목록 조회
        KakaoTalkService.getInstance()
                .requestAppFriends(context, new TalkResponseCallback<AppFriendsResponse>() {
                    @Override
                    public void onSuccess(AppFriendsResponse result) {
                        Log.i("KAKAO_API", "친구 조회 성공");

                        for (AppFriendInfo friend : result.getFriends()) {
                            Log.d("KAKAO_API", friend.toString());

                            Log.d("KAKAO_API", "친구 닉네임 : " + friend.getProfileNickname());

                            String uuid = friend.getUUID();     // 메시지 전송 시 사용

                            Log.d("KAKAO_API", "친구 UUID : " + uuid);
                            uuids.add(uuid);

                            Log.d("KAKAO_API", "uuids 정보 : " + uuids);
                        }

                        if (context.hasNext()) {
                            requestNextFriends(context);
                        }
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                    }

                    @Override
                    public void onNotKakaoTalkUser() {
                        Log.e("KAKAO_API", "카카오톡 사용자가 아님");
                    }

                    // 중략

                });

    }


}

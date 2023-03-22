package com.gdsc.wero.domain.user.domain;

import com.gdsc.wero.domain.common.BaseTimeEntity;
import com.gdsc.wero.domain.mbti.domain.Mbti;
import com.gdsc.wero.domain.user.api.dto.request.UserInfoReqDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity implements Persistable<String> {

    @Id
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_name")
    private String name;
    private String picture;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role;
    private String provider; //Auth 종류(google, naver 등)
    @Embedded
    private UserInfo userInfo;
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Mbti mbti;

    @Builder
    private User(String name, String email, String picture, Role role, String provider, UserInfo userInfo) {
        this.name = name;
        this.email = email;
        this.provider = provider;
        this.role = role;
        this.picture = picture;
        this.userInfo = userInfo;

    }


    /**
     * User 생성 메서드
     */
    public static User createUser(String email, String name, String picture, String provider) {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
//                .role(Role.GUEST) // 처음 가입할 때(로그인할 때) GUEST권한 부여
                .role(Role.USER)
                .provider(provider)
                .userInfo(null)
                .build();
    }

    /**
     * User 수정 메서드
     */
    public User update(String name, String email, String picture, String provider) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.provider = provider;

        return this;
    }

    /**
     * User Info 등록 및 수정 메서드
     */
    public void updateUserInfo(UserInfoReqDto userInfoReqDto){
        this.userInfo = new UserInfo(userInfoReqDto); // 애매...

    }


    /**
     * Role 조회 메서드
     */
    public String getRoleKey(){
        return this.role.getKey();
    }


    /**
     * @GeneratedValue면 save()호출 시 식별자가 없으므로 새로운 엔티티로 인식해 persist가 정상 작동한다.
     * 하지만 @Id만 사용해 직접 할당하면 이미 식별자가 있는 상태로 save()가 호출된다. 따라서 이 경우는 merge가 호츨된다.
     * merge()는 우선 DB를 호출해 값을 확인하고, DB에 없다면 새로운 엔티티로 인지하기 때문에 매우 비효율적이다.
     * 따라서 Persistable을 사용해 새로운 엔티티 확인 여부를 직접 구현하는게 효과적이다.
     * 참고로 @CreatedDate를 조합해서 사용하면 편리하게 사용할 수 있다!
     * @return
     */
    @Override
    public String getId() {
        return email;
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }
}
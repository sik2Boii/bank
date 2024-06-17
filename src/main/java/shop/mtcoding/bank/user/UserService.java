package shop.mtcoding.bank.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 비지니스 로직 처리, 트랜잭션 처리
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO reqDTO) {

        // STEP: 두번째 단계
        User user = userRepository.findByUsername(reqDTO.getUsername());
        if (user != null) throw new RuntimeException("유저네임 중복되었습니다 : " + reqDTO.getUsername());

        userRepository.save(reqDTO.getUsername(), reqDTO.getPassword(), reqDTO.getEmail(), reqDTO.getFullname());
    }

    public User 로그인(UserRequest.LoginDTO reqDTO) {
        return userRepository.findByUsernameAndPassword(reqDTO.getUsername(), reqDTO.getPassword());
    }
}
package com.heon.sns.service;


import com.heon.sns.exception.ErrorCode;
import com.heon.sns.exception.SnsApplicationException;
import com.heon.sns.model.entity.PostEntity;
import com.heon.sns.model.entity.UserEntity;
import com.heon.sns.repository.PostEntityRepository;
import com.heon.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void create(String title, String body, String userName) {

        // user find
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));

        // post save
        postEntityRepository.save(PostEntity.of(title, body, userEntity))

        // return

    }

}
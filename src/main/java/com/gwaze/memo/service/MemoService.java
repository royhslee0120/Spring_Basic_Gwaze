package com.gwaze.memo.service;

import com.gwaze.member.entity.Member;
import com.gwaze.member.repository.MemberRepository;
import com.gwaze.memo.dto.MemoCreateRequest;
import com.gwaze.memo.dto.MemoCreateResponse;
import com.gwaze.memo.dto.MemoGetResponse;
import com.gwaze.memo.entity.Memo;
import com.gwaze.memo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MemoCreateResponse save(MemoCreateRequest request) {
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(
                () -> new IllegalStateException("없는 멤버입니다.")
        );
        Memo memo = new Memo(request.getText(), member);
        Memo savedMemo = memoRepository.save(memo);
        return new MemoCreateResponse(savedMemo.getId(), savedMemo.getText());
    }

    @Transactional(readOnly = true)
    public MemoGetResponse findOne(Long memoId) {
        Memo memo = memoRepository.findById(memoId).orElseThrow(
                () -> new IllegalStateException("없는 메모입니다.")
        );
        Member member = memo.getMember();
        return new MemoGetResponse(
                memo.getId(),
                memo.getText(),
                member.getId(),
                member.getName()
        );
    }
}

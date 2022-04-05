package com.utudo.hwwd.service.impl;

import com.utudo.hwwd.helpers.HwTools;
import com.utudo.hwwd.models.MatchOption;
import com.utudo.hwwd.repository.MatchOptionRepository;
import com.utudo.hwwd.service.MatchOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MatchOptionServiceImpl implements MatchOptionService {

    @Autowired
    private MatchOptionRepository matchOptionRepository;

    @Override
    public void save(MatchOption matchOption) {
        String time = HwTools.getTime();
        matchOption.setCtime(time);
        matchOption.setUtime(time);
        matchOptionRepository.save(matchOption);
    }

    @Override
    public void update(MatchOption matchOption) {
        String time = HwTools.getTime();
        matchOption.setUtime(time);
        matchOptionRepository.save(matchOption);
    }

    @Override
    public MatchOption findOneIfExist(long aid, long pid, long nid) {
        Optional<MatchOption> matchOption = matchOptionRepository.findIfExistParamsNative(aid,pid,nid);
        return matchOption.orElseGet(MatchOption::new);
    }

    @Override
    public ArrayList<MatchOption> getMatchOptions(long aid, long pid) {
        return matchOptionRepository.getMatchOptionsByPidAndAidParamsNative(aid,pid);
    }


}

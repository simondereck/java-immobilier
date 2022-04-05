package com.utudo.hwwd.service;

import com.utudo.hwwd.models.MatchOption;

import java.util.ArrayList;

public interface MatchOptionService {
    public void save(MatchOption matchOption);
    public void update(MatchOption matchOption);
    public MatchOption findOneIfExist(long aid,long pid,long nid);

    public ArrayList<MatchOption> getMatchOptions(long aid,long pid);
}

package cn.acyou.iblog.service.impl;

import cn.acyou.iblog.service.ActiveCodeService;
import org.springframework.stereotype.Service;

/**
 * @author youfang
 * @create 2018-02-08 22:49
 */
@Service("activeCodeService")
public class ActiveCodeServiceImpl implements ActiveCodeService{

    @Override
    public String sendActiveCode() {
        return "DDFG";
    }
}

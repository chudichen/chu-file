package com.chudichen.chufile.respository;

import com.chudichen.chufile.BaseTest;
import com.chudichen.chufile.model.entity.DriveConfig;
import com.chudichen.chufile.repository.DriveConfigRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author chudichen
 * @date 2021-01-25
 */
public class DriveConfigRepositoryTest extends BaseTest {

    @Autowired
    private DriveConfigRepository driveConfigRepository;

    @Test
    public void findAllTest() {
        List<DriveConfig> all = driveConfigRepository.findAll();
        System.out.println(all);
    }
}

package com.frizzer.employeeapp.service;

import com.frizzer.employeeapp.entity.employeeinfo.EmployeeInfo;
import com.frizzer.employeeapp.entity.employeeinfo.EmployeeInfoDto;
import com.frizzer.employeeapp.exception.EmployeeApplicationException;
import com.frizzer.employeeapp.mapper.EmployeeInfoMapper;
import com.frizzer.employeeapp.repository.EmployeeInfoRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.transaction.Transactional;
import java.util.List;

@Stateless
public class EmployeeInfoService {

  @EJB
  private EmployeeInfoRepository employeeInfoRepository;

  @Transactional
  public EmployeeInfoDto save(EmployeeInfoDto employeeInfoDto, Long id) {
    EmployeeInfo employeeInfo = EmployeeInfoMapper.INSTANCE.fromDto(employeeInfoDto);
    employeeInfo.setId(id);
    return EmployeeInfoMapper.INSTANCE.toDto(employeeInfoRepository.save(employeeInfo));
  }

  @Transactional
  public EmployeeInfoDto update(EmployeeInfoDto employeeInfoDto, Long id) {
    EmployeeInfo employeeInfo = EmployeeInfoMapper.INSTANCE.fromDto(employeeInfoDto);
    employeeInfo.setId(id);
    return EmployeeInfoMapper.INSTANCE.toDto(employeeInfoRepository.update(employeeInfo));
  }

  @Transactional
  public void delete(Long id) {
    employeeInfoRepository.delete(id);
  }

  public EmployeeInfoDto findById(Long id) {
    EmployeeInfo employeeInfo = employeeInfoRepository.findById(id)
        .orElseThrow(() -> new EmployeeApplicationException("Employee info not found"));
    return EmployeeInfoMapper.INSTANCE.toDto(employeeInfo);
  }

  public List<EmployeeInfoDto> findAll() {
    return employeeInfoRepository.findAll().stream().map(EmployeeInfoMapper.INSTANCE::toDto).toList();
  }

  public boolean checkIfEmployeeInfoExists(Long id) {
    return employeeInfoRepository.findById(id).isPresent();
  }

}

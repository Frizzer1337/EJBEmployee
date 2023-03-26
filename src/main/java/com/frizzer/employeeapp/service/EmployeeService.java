package com.frizzer.employeeapp.service;

import com.frizzer.employeeapp.entity.employee.Employee;
import com.frizzer.employeeapp.entity.employee.EmployeeRequestDto;
import com.frizzer.employeeapp.entity.employee.EmployeeResponseDto;
import com.frizzer.employeeapp.mapper.EmployeeMapper;
import com.frizzer.employeeapp.repository.EmployeeRepository;
import com.frizzer.employeeapp.security.JwtTokenService;
import com.frizzer.employeeapp.security.PasswordEncryptService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.transaction.Transactional;
import java.util.List;

@Stateless
public class EmployeeService {

  @EJB
  private EmployeeRepository employeeRepository;
  @EJB
  private JwtTokenService tokenService;
  @EJB
  private PasswordEncryptService encryptService;

  @Transactional
  public EmployeeResponseDto save(EmployeeRequestDto employeeDto) {
    Employee employee = EmployeeMapper.INSTANCE.fromRequestDto(employeeDto);
    employee.setPassword(encryptService.encrypt(employee.getPassword()));
    return EmployeeMapper.INSTANCE.toResponseDto(
        employeeRepository.save(employee));
  }

  @Transactional
  public EmployeeResponseDto update(EmployeeRequestDto employeeResponseDto, Long id) {
    Employee employee = EmployeeMapper.INSTANCE.fromRequestDto(employeeResponseDto);
    employee.setId(id);
    return EmployeeMapper.INSTANCE.toResponseDto(employeeRepository.save(employee));
  }


  @Transactional
  public boolean delete(Long id) {
    return employeeRepository.delete(id);
  }


  public String generateToken(EmployeeRequestDto employeeRequestDto) {
    Employee entity = employeeRepository.findByLogin(employeeRequestDto.getLogin());
    return tokenService.generateToken(entity.getLogin(), entity.getRole());
  }

  public boolean checkIfPasswordCorrect(EmployeeRequestDto employeeRequestDto) {
    Employee entity = employeeRepository.findByLogin(employeeRequestDto.getLogin());
    return entity != null && encryptService.checkPassword(employeeRequestDto.getPassword(), entity.getPassword());
  }

  public EmployeeResponseDto findById(Long id) {
    return EmployeeMapper.INSTANCE.toResponseDto(employeeRepository.findById(id));
  }

  public List<EmployeeResponseDto> findAll() {
    return employeeRepository.findAll()
        .stream()
        .map(EmployeeMapper.INSTANCE::toResponseDto)
        .toList();
  }

}

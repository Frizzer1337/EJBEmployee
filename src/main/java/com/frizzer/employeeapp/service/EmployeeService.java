package com.frizzer.employeeapp.service;

import com.frizzer.employeeapp.entity.Employee;
import com.frizzer.employeeapp.entity.EmployeeRequestDto;
import com.frizzer.employeeapp.entity.EmployeeResponseDto;
import com.frizzer.employeeapp.mapper.EmployeeMapper;
import com.frizzer.employeeapp.repository.EmployeeRepository;
import com.frizzer.employeeapp.security.JwtTokenService;
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

  @Transactional
  public EmployeeResponseDto save(EmployeeRequestDto employee) {
    return EmployeeMapper.INSTANCE.toResponseDto(
        employeeRepository.save(EmployeeMapper.INSTANCE.fromRequestDto(employee)));
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
    return entity != null && entity.getPassword().equals(employeeRequestDto.getPassword());
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

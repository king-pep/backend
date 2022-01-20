package za.co.fnb.services;

import za.co.fnb.entities.EmployeeRoleMapping;
import za.co.fnb.repositories.EmployeeRoleMappingRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeRoleMappingService {

    private final EmployeeRoleMappingRepository employeeRoleMappingRepository;

    @Autowired
    public EmployeeRoleMappingService(EmployeeRoleMappingRepository employeeRoleMappingRepository) {
        this.employeeRoleMappingRepository = employeeRoleMappingRepository;
    }

    public List<EmployeeRoleMapping> findAllEmployeeRoleMappings() {
        return employeeRoleMappingRepository.findAll();
    }

    public Optional<EmployeeRoleMapping> findEmployeeRoleMappingById(Long id) {
        return employeeRoleMappingRepository.findById(id);
    }

    public EmployeeRoleMapping saveEmployeeRoleMapping(EmployeeRoleMapping employeeRoleMapping) {
        return employeeRoleMappingRepository.save(employeeRoleMapping);
    }

    public void deleteEmployeeRoleMappingById(Long id) {
        employeeRoleMappingRepository.deleteById(id);
    }
}

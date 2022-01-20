package za.co.fnb.services;

import za.co.fnb.entities.RoleMaster;
import za.co.fnb.repositories.RoleMasterRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleMasterService {

    private final RoleMasterRepository roleMasterRepository;

    @Autowired
    public RoleMasterService(RoleMasterRepository roleMasterRepository) {
        this.roleMasterRepository = roleMasterRepository;
    }

    public List<RoleMaster> findAllRoleMasters() {
        return roleMasterRepository.findAll();
    }

    public Optional<RoleMaster> findRoleMasterById(Long id) {
        return roleMasterRepository.findById(id);
    }

    public RoleMaster saveRoleMaster(RoleMaster roleMaster) {
        return roleMasterRepository.save(roleMaster);
    }

    public void deleteRoleMasterById(Long id) {
        roleMasterRepository.deleteById(id);
    }
}

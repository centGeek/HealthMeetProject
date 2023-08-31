package com.HealthMeetProject.code.business;

import com.HealthMeetProject.code.api.dto.DoctorDTO;
import com.HealthMeetProject.code.api.dto.PatientDTO;
import com.HealthMeetProject.code.api.dto.UserData;
import com.HealthMeetProject.code.domain.exception.UserAlreadyExistsException;
import com.HealthMeetProject.code.infrastructure.database.entity.UserEntity;
import com.HealthMeetProject.code.infrastructure.security.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(DoctorDTO user) throws UserAlreadyExistsException {
        if(checkIfUserExists(user.getEmail())){
            throw new UserAlreadyExistsException("User already exists for this email");
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userRepository.save(userEntity);
    }

    @Override
    public void register(PatientDTO patientDTO) throws UserAlreadyExistsException {

    }

    @Override
    public boolean checkIfUserExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
    private void setPasswordEncoder(UserData source, UserEntity target){
        target.setPassword(passwordEncoder.encode(source.getPassword()));
    }
}

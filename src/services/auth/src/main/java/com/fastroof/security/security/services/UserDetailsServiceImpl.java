package com.fastroof.security.security.services;

import com.fastroof.security.models.User;
import com.fastroof.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The UserDetailsServiceImpl Class.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /** The user repository. */
    private final UserRepository userRepository;

	/**
	 * Instantiates a new user details service impl.
	 *
	 * @param userRepository the user repository
	 */
	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Load user by username.
	 *
	 * @param email the email
	 * @return the user details
	 * @throws UsernameNotFoundException the username not found exception
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

		return UserDetailsImpl.build(user);
	}

}

    package com.railtrack.railtrackohe.entity;

	import jakarta.persistence.*;
	import lombok.*;

	@Entity
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	@Table(name = "users")
	public class User {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String name;

	    @Column(unique = true, nullable = false)
	    private String email;

	    @Column(nullable = false)
	    private String password;

	    @Enumerated(EnumType.STRING)
	    private Role role;

	    private String phone;

	    @Builder.Default
	    @Column(name = "is_verified")
	    private boolean isVerified = false;
	
}

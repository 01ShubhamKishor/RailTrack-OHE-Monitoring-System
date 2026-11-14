    package com.railtrack.railtrackohe.entity;

	import jakarta.persistence.*;
	import lombok.*;
	import java.time.LocalDateTime;

	@Entity
	@Table(name = "users")
	@Data 
	@NoArgsConstructor
	@AllArgsConstructor 
	@Builder
	public class User {
	    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String name;
	    
	    @Column(unique = true)
	    private String email;
	    
	    @Column(name = "employee_id", unique = true)
	    private String employeeId;
	    
	    private String password;

	    @Enumerated(EnumType.STRING)
	    private Role role;

	    private String department;
	    
	    private String phone;
	    
	    @Column(name = "is_approved")
	    private Boolean isApproved = false;
	    
	    @Column(name = "created_at")
	    private LocalDateTime createdAt = LocalDateTime.now();

	 
	}


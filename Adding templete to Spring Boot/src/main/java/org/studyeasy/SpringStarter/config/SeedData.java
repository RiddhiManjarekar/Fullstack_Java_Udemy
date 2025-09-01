package org.studyeasy.SpringStarter.config;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.studyeasy.SpringStarter.models.Account;
import org.studyeasy.SpringStarter.models.Authority;
import org.studyeasy.SpringStarter.models.Post;
import org.studyeasy.SpringStarter.security.util.constants.Privileges;
import org.studyeasy.SpringStarter.security.util.constants.Roles;
import org.studyeasy.SpringStarter.services.AccountService;
import org.studyeasy.SpringStarter.services.AuthorityService;
import org.studyeasy.SpringStarter.services.PostService;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public void run(String... args) throws Exception {

        for (Privileges auth : Privileges.values()) {
            Authority authority = new Authority();
            authority.setId(auth.getId());
            authority.setName(auth.getPrivilege());
            authorityService.save(authority);
        }

        Account account01 = new Account();
        Account account02 = new Account();
        Account account03 = new Account();
        Account account04 = new Account();

        account01.setEmail("user@user.com");
        account01.setPassword("pass987");
        account01.setFirstname("user");
        account01.setLastname("lastname");
        account01.setAge(25);
        account01.setDate_of_birth(LocalDate.parse("1990-01-01"));
        account01.setGender("Male");

        account02.setEmail("admin@admin.com");
        account02.setPassword("pass987");
        account02.setFirstname("admin");
        account02.setLastname("lastname");
        account02.setRole(Roles.ADMIN.getRole());
        account02.setAge(25);
        account02.setDate_of_birth(LocalDate.parse("1990-01-01"));
        account02.setGender("Female");


        account03.setEmail("editor@editor.com");
        account03.setPassword("pass987");
        account03.setFirstname("editor");
        account03.setLastname("lastname");
        account03.setRole(Roles.EDITOR.getRole());
        account03.setAge(55);
        account03.setDate_of_birth(LocalDate.parse("1975-01-01"));
        account03.setGender("Male");


        account04.setEmail("super_editor@editor.com");
        account04.setPassword("pass987");
        account04.setFirstname("super_editor");
        account04.setLastname("lastname");
        account04.setRole(Roles.EDITOR.getRole());
        Set<Authority> authorities=new HashSet<>();
        authorityService.findById(Privileges.RESET_ANY_USER_PASSWORD.getId()).ifPresent(authorities::add);
        authorityService.findById(Privileges.ACCESS_ADMIN_PANEL.getId()).ifPresent(authorities::add);
        account04.setAuthorities(authorities);
        account04.setAge(40);
        account04.setDate_of_birth(LocalDate.parse("1980-01-01"));
        account04.setGender("Female");


        accountService.save(account01);
        accountService.save(account02);
        accountService.save(account03);
        accountService.save(account04);

        List<Post> posts = postService.findAll();
        if (posts.size() == 0) {
            Post post01 = new Post();
            post01.setTitle("About Git");
            post01.setBody("""
                Git (/ɡɪt/[8]) is a distributed version control system[9] that tracks versions of files.
                It is often used to control source code by programmers who are developing software collaboratively.
                        
                        """);
            post01.setAccount(account01);
            postService.save(post01);

            Post post02 = new Post();
            post02.setTitle("Springboot MVC Framework");
            post02.setBody("""
                <h3><strong>Model-View-Controller Framework</strong></h3>
                <p><a href="https://en.wikipedia.org/wiki/Spring_Framework"
                """);
            post02.setAccount(account02);
            postService.save(post02);

            Post post03 = new Post();
            post03.setTitle("Java (Programming Language)");
            post03.setBody("""
                From Wikipedia, the free encyclopedia
This article is about the object-oriented programming language. For the software platform, see Java (software platform). For the Indonesian island, see Java. For the Indonesian Language, see Javanese. For other uses, see Java (disambiguation).
Not to be confused with JavaScript.
"Openframe" redirects here. For the ten-pin bowling term, see Open frame.
Java
Paradigm	Multi-paradigm: generic, object-oriented (class-based), functional, imperative, reflective, concurrent
Designed by	James Gosling
Developer	Oracle Corporation
First appeared	May 23, 1995; 30 years ago[1]
Stable release	
Java SE 24[2][3] Edit this on Wikidata / 18 March 2025; 4 months ago
Typing discipline	Static, strong, safe, nominative, manifest
Memory management	Automatic garbage collection
Filename extensions	.java, .class, .jar, .jmod, .war
Website	
oracle.com/java/
java.com
dev.java
Influenced by
CLU,[4] Simula67,[4] Lisp,[4] Smalltalk,[4] Ada 83, C++,[5] C#,[6] Eiffel,[7] Mesa,[8] Modula-3,[9] Oberon,[10] Objective-C,[11] UCSD Pascal,[12][13] Object Pascal[14]
Influenced
Ada 2005, ArkTS, BeanShell, C#, Chapel,[15] Clojure, ECMAScript, Fantom, Gambas,[16] Groovy, Hack,[17] Haxe, J#, JavaScript, JS++, Kotlin, PHP, Python, Scala, Seed7, Vala
 Java Programming at Wikibooks
Java is a high-level, general-purpose, memory-safe, object-oriented programming language. It is intended to let programmers write once, run anywhere (WORA),[18] meaning that compiled Java code can run on all platforms that support Java without the need to recompile.[19] Java applications are typically compiled to bytecode that can run on any Java virtual machine (JVM) regardless of the underlying computer architecture. The syntax of Java is similar to C and C++, but has fewer low-level facilities than either of them. The Java runtime provides dynamic capabilities (such as reflection and runtime code modification) that are typically not available in traditional compiled languages.
                """);
            post03.setAccount(account02);
            postService.save(post03);

            Post post04 = new Post();
            post04.setTitle("HIbernate");
            post04.setBody("""
                Hibernate ORM (or simply Hibernate) is an object–relational mapping[2]: §1.2.2, [12]  tool for the Java programming language. It provides a framework for mapping an object-oriented domain model to a relational database. Hibernate handles object–relational impedance mismatch problems by replacing direct, persistent database accesses with high-level object handling functions.
                """);
            post04.setAccount(account02);
            postService.save(post04);

            Post post05 = new Post();
            post05.setTitle("JPA");
            post05.setBody("""
                Jakarta Persistence, also known as JPA (abbreviated from the former name Java Persistence API) is a Jakarta EE application programming interface specification that describes the management of relational data in enterprise Java applications.

Persistence in this context covers three areas:

The API itself, defined in the jakarta.persistence package (javax.persistence for Jakarta EE 8 and below)
The Jakarta Persistence Query Language (JPQL; formerly Java Persistence Query Language)
Object/relational metadata
                """);
            post05.setAccount(account02);
            postService.save(post05);

            Post post06 = new Post();
            post06.setTitle("MySQL");
            post06.setBody("""
                MySQL (/ˌmaɪˌɛsˌkjuːˈɛl/)[6] is an open-source relational database management system (RDBMS).[6][7] Its name is a combination of "My", the name of co-founder Michael Widenius's daughter My,[1] and "SQL", the acronym for Structured Query Language. A relational database organizes data into one or more data tables in which data may be related to each other; these relations help structure the data. SQL is a language that programmers use to create, modify and extract data from the relational database, as well as control user access to the database. In addition to relational databases and SQL, an RDBMS like MySQL works with an operating system to implement a relational database in a computer's storage system, manages users, allows for network access and facilitates testing database integrity and creation of backups.
                """);
            post06.setAccount(account02);
            postService.save(post06);
            
        }
    }
}

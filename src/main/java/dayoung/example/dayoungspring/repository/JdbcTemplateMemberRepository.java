package dayoung.example.dayoungspring.repository;

import dayoung.example.dayoungspring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());
        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;

    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> reuslt = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return reuslt.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> reuslt = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return reuslt.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        List<Member> reuslt = jdbcTemplate.query("select * from member", memberRowMapper());
        return reuslt;
    }

    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}

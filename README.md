### Jdbc PreparedStatement way
* Step1: Prepare RETURN_GENERATED_KEYS statement using insertSql

```java
String insertSql = "insert into t_user (username) values('zhangsan')";
PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
```
* Step2: Execute insertSql using statement

```java
pstmt.executeUpdate();
```
* Step3: Retrieve auto-generated keys

```java
ResultSet rs = pstmt.getGeneratedKeys();
Assert.assertTrue(rs.getLong(1) > 0);
```
### Spring JdbcTemplate way

```java
final String insertSql = "insert into t_user (username) values(?)";
KeyHolder keyHolder = new GeneratedKeyHolder();
jdbcTemplate.update(new PreparedStatementCreator() {
    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(insertSql, new String[] { "id" });
        ps.setString(1, 'zhangsan');
        return ps;
	}
}, keyHolder);
Assert.assertTrue(keyHolder.getKey().longValue() > 0);
```

##### For run JUnit Test a greenbar, config db parameters on below places:
	JdbcUtilTest.java setUpBeforeClass method
	application-beans.xml dataSource bean
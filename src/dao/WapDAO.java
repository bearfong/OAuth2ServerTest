package dao;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

@Component
public class WapDAO implements WapDAOInterface{
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
	public String addAuthCode(String authResponseCode, String scope ){
		Session session = sessionFactory.openSession();
		
		String sql = "select * from oauthaccountinfo";
		
		Query query = session.createSQLQuery(sql);
		List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		Map<String, Object> map = list.get(0);
		
		System.out.println(MapUtils.getString(map, "name"));
		
		return null;
	}
	
}

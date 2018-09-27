package cn.itcast.core.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.itcast.core.dao.UserDao;
import cn.itcast.core.mapper.UserMapper;
import cn.itcast.core.pojo.User;
@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private SolrServer solrServer;
	@Override
	public void save(User user) {
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", user.getId());
		document.addField("name", user.getName());
		document.addField("birthday", user.getBirthday());
		try {
			solrServer.add(document);
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	@Override
	public List<User> findAll() {
		List<User> userList = new ArrayList<>();
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("*:*");
		try {
			QueryResponse queryResponse = solrServer.query(solrQuery);
			SolrDocumentList results = queryResponse.getResults();
			for (SolrDocument solrDocument : results) {
				User user = new User();
				if(solrDocument.get("id")!=null && !"".equals(solrDocument.get("id"))){
					user.setId(Integer.parseInt(solrDocument.get("id").toString()));
				}
				if(solrDocument.get("username")!=null && !"".equals(solrDocument.get("name"))){
				user.setName(solrDocument.get("name").toString());
				}
				if(solrDocument.get("birthday")!=null && !"".equals(solrDocument.get("birthday"))){
					SimpleDateFormat sf =new SimpleDateFormat("yyyy-MM-dd");
					Date date =sf.parse(solrDocument.get("birthday").toString());
					user.setBirthday(date);
				}
				userList.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

}

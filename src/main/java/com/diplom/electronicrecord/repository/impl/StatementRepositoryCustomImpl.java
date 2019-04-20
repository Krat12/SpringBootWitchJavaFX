package com.diplom.electronicrecord.repository.impl;

import com.diplom.electronicrecord.model.Group;
import com.diplom.electronicrecord.model.Statement;
import com.diplom.electronicrecord.model.Subject;
import com.diplom.electronicrecord.repository.StatementRepositoryCustom;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StatementRepositoryCustomImpl implements StatementRepositoryCustom {


    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public List<Statement> findByCriteria(String type, Subject subject, Group group, Date startDate, Date endDate, String typeUser, Long teacherId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        boolean isFirst = true;

        StringBuilder query = new StringBuilder("from Statement st join fetch st.subject s join fetch st.group g join fetch st.teacher t ");

        if (type != null) {

            query = isFirst ? query.append("where st.type = '"+type+"' ") : query.append("and st.type = '"+type+"' ");

            isFirst = false;
        }
        if (subject != null) {
            query = isFirst ? query.append("where s.id = "+subject.getId()+" ") :
                    query.append("and s.id = "+subject.getId()+" ");

            isFirst = false;
        }
        if (group != null) {
            query = isFirst ? query.append("where g.id = "+group.getId()+" ") :
                    query.append("and g.id = "+group.getId()+" ");

            isFirst = false;
        }
        if (startDate != null) {
            query = isFirst ? query.append("where st.date >= '"+simpleDateFormat.format(startDate.getTime())+"' ") :
                    query.append("and st.date >= '"+simpleDateFormat.format(startDate.getTime())+"' ");

            isFirst = false;
        }
        if (endDate != null) {
            query = isFirst ? query.append("where st.date <= '" + simpleDateFormat.format(endDate.getTime()) +"' ") :
                    query.append("and st.date <= '" +simpleDateFormat.format(endDate.getTime())+"' ");

            isFirst = false;
        }
        if(typeUser.equals("Teacher")){
            query = isFirst ? query.append("where t.id = "+teacherId+" "):query.append("and t.id = "+teacherId+" ");
            isFirst = false;
        }

        query = isFirst ? query.append("where g.status = 'обучаются' "):query.append("and g.status = 'обучаются' ");

        return em.createQuery(query.toString(),Statement.class).getResultList();
    }
}

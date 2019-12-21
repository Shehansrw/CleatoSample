package com.authapi.demo.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao<PK extends Serializable, T> {

	private final Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public AbstractDao() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	protected Criteria createEntityCriteria(boolean ignoreInactive, String activeField) {
		Criteria criteria = getSession().createCriteria(persistentClass);
		if (ignoreInactive) {
			criteria.add(Restrictions.ne(activeField, 'i'));
		}
		return criteria;
	}
	
	protected Criteria createEntityCriteriaWithBoolean(boolean ignoreInactive, String activeField) {
		Criteria criteria = getSession().createCriteria(persistentClass);
		if (ignoreInactive) {
			criteria.add(Restrictions.ne(activeField, false));
		}
		return criteria;
	}

	protected Criteria createEntityCriteriaWithAlias(boolean ignoreInactive, String activeField, String alias) {
		Criteria criteria = getSession().createCriteria(persistentClass, alias);
		if (ignoreInactive) {
			criteria.add(Restrictions.ne(activeField, 'i'));
		}
		return criteria;
	}

	@SuppressWarnings("unchecked")
	public T getByKey(PK key, boolean ignoreInactive, String activeField) {
		Criteria criteria = getSession().createCriteria(persistentClass);
		if (ignoreInactive) {
			criteria.add(Restrictions.ne(activeField, 'i'));
		}
		return (T) getSession().get(persistentClass, key);
	}

	public void persist(T entity) {
		getSession().persist(entity);
	}

	public void persistBulk(List<T> entities) {
	    for (T entity : entities) {
            getSession().persist(entity);
        }
    }

	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}

	public void update(T entity) {
		getSession().update(entity);
	}

	public void merge(T entity) {
		getSession().merge(entity);
	}

	public void mergeBulk(List<T> entities) {
	    for (T entity : entities) {
	        getSession().merge(entity);
        }
    }

	public Object mergeNreturn(T entity) {
		return getSession().merge(entity);
	}

	public void delete(T entity) {
		getSession().delete(entity);
	}

	public void deleteBulk(List<T> entities) {
		for (T entity : entities) {
	        getSession().delete(entity);
        }
	}

//	public List<T> auditReader(long id) {
//		AuditReader auditReader = AuditReaderFactory.get(getSession());
//		List<Number> revisions = auditReader.getRevisions(persistentClass, id);
//		List<T> revisionList = new ArrayList<T>();
//		for (Number revisionNumber : revisions) {
//			T entity = auditReader.find(persistentClass, id, revisionNumber);
//			revisionList.add(entity);
//		}
//		return revisionList;
//	}
//
//	public void changesFromAudit(List<Long> ids, long objId) {
//		if (ids != null && !ids.isEmpty()) {
//			for (Long id : ids) {
//				auditDetails(id);
//			}
//		} else if (objId > 0) {
//			auditDetails(objId);
//		}
//	}
//
//	private void auditDetails(long id) {
//		System.out.println("Changes From : " + persistentClass.getSimpleName() + " -------- > ID : " + id);
//		
//		List<T> audits = auditReader(id);
//		
//		T previous = null;
////		T current = null;
////		boolean isCurrent = false;
////		
////		for(T row : audits) {
////			
////			if (isCurrent) {
////				previous = row;
////				isCurrent = false;
////			} else {
////				current = row;
////				isCurrent = true;
////			}
////			
////			if (previous != null && current != null) {
////				
////			}
////		}
//	}
//
////	Utility for Report Creation
//
//	protected void aliasCreatorForReport(Criteria criteria, ReportConfiguration reportConfiguration) {
//		for (ReportAlias reportAlias : reportConfiguration.getReportAliases()) {
//			if (reportAlias.getJoinType() != null) {
//				criteria.createAlias(reportAlias.getAssociationPath(), reportAlias.getAlias(), reportAlias.getJoinType());
//			} else {
//				criteria.createAlias(reportAlias.getAssociationPath(), reportAlias.getAlias());
//			}
//		}
//	}
//
//	protected void filterCreatorForReport(Criteria criteria, ReportConfiguration reportConfiguration, Map<String, Object> searchMap) {
//		if(reportConfiguration.getReportFilterTrans() != null && !reportConfiguration.getReportFilterTrans().isEmpty()) {
//			for (ReportFilter reportFilter : reportConfiguration.getReportFilterTrans()) {
//				switch (reportFilter.getReportGroupFilter().getFilterType()) {
//				case TEXT:
//					criteria.add(Restrictions.ilike(reportFilter.getReportGroupFilter().getAssociationPath(), 
//							searchMap.get(reportFilter.getReportGroupFilter().getParameterName())));
//					break;
//					
//				case NUMBERRANGE:
//					if ((double) searchMap.get(reportFilter.getReportGroupFilter().getParameterName() + "Min") > 0 && 
//							(double) searchMap.get(reportFilter.getReportGroupFilter().getParameterName() + "Max") > 0) {
//						criteria.add(Restrictions.between(reportFilter.getReportGroupFilter().getAssociationPath(),
//								(double) searchMap.get(reportFilter.getReportGroupFilter().getParameterName() + "Min"),
//								(double) searchMap.get(reportFilter.getReportGroupFilter().getParameterName() + "Max")));
//						
//					} else if ((double) searchMap.get(reportFilter.getReportGroupFilter().getParameterName() + "Min") > 0) {
//						criteria.add(Restrictions.ge(reportFilter.getReportGroupFilter().getAssociationPath(), 
//								(double) searchMap.get(reportFilter.getReportGroupFilter().getParameterName() + "Min")));
//						
//					} else if ((double) searchMap.get(reportFilter.getReportGroupFilter().getParameterName() + "Max") > 0) {
//						criteria.add(Restrictions.le(reportFilter.getReportGroupFilter().getAssociationPath(), 
//								(double) searchMap.get(reportFilter.getReportGroupFilter().getParameterName() + "Max")));
//						
//					}
//					break;
//					
//				case DATERANGE:
//					if ((long) searchMap.get(reportFilter.getReportGroupFilter().getParameterName() + "Min") > 0 &&
//							(long) searchMap.get(reportFilter.getReportGroupFilter().getParameterName() + "Max") > 0) {
//						criteria.add(Restrictions.between(reportFilter.getReportGroupFilter().getAssociationPath(), 
//								(long) searchMap.get(reportFilter.getReportGroupFilter().getParameterName() + "Min"),
//								(long) searchMap.get(reportFilter.getReportGroupFilter().getParameterName() + "Max")));
//						
//					} else if ((long) searchMap.get(reportFilter.getReportGroupFilter().getParameterName() + "Min") > 0) {
//						criteria.add(Restrictions.ge(reportFilter.getReportGroupFilter().getAssociationPath(), 
//								(long) searchMap.get(reportFilter.getReportGroupFilter().getParameterName() + "Min")));
//						
//					} else if ((long) searchMap.get(reportFilter.getReportGroupFilter().getParameterName() + "Max") > 0) {
//						criteria.add(Restrictions.le(reportFilter.getReportGroupFilter().getAssociationPath(), 
//								(long) searchMap.get(reportFilter.getReportGroupFilter().getParameterName() + "Max")));
//						
//					}
//					
//					break;
//				case SELECT:
//					criteria.add(Restrictions.eq(reportFilter.getReportGroupFilter().getAssociationPath(), 
//							(long) searchMap.get(reportFilter.getReportGroupFilter().getParameterName())));
//					
//					break;
//				case SELECTTEXT:
//					criteria.add(Restrictions.eq(reportFilter.getReportGroupFilter().getAssociationPath(), 
//							searchMap.get(reportFilter.getReportGroupFilter().getParameterName())));
//					
//					break;
//				case SELECTMULTIPLE:
//				case CHECKBOXES:
//					criteria.add(Restrictions.in(reportFilter.getReportGroupFilter().getAssociationPath(), 
//							(List<Long>) searchMap.get(reportFilter.getReportGroupFilter().getParameterName())));
//					
//					break;
//				case RADIOOPTION:
//					criteria.add(Restrictions.in(reportFilter.getReportGroupFilter().getAssociationPath(), 
//							(List<String>) searchMap.get(reportFilter.getReportGroupFilter().getParameterName())));
//					
//					break;
//				case CHECKBOX:
//					criteria.add(Restrictions.eq(reportFilter.getReportGroupFilter().getAssociationPath(), 
//							(boolean) searchMap.get(reportFilter.getReportGroupFilter().getParameterName())));
//					
//					break;
//				}
//			}
//		}
//	}
//
//	protected void orderNProjectionsCreatorForReport(Criteria criteria, ReportConfiguration reportConfiguration,
//			ProjectionList projectionList, String sortField, String sortOrder, List<ReportColumn> colorColumns) {
//		boolean isAscendingSort = true;
//		boolean isSortAdding = true;
//		String sortColumn = sortField;
//		if (sortField.equals("sorting_desc")) {
//			isAscendingSort = true;
//		} else if (sortOrder.equals("sorting_asc")) {
//			isAscendingSort = false;
//		}
//		
//		Set<ReportColumnOrder> defaultColumnOrders = new HashSet<>();
//		ReportColumn defaultOrderColumn = null;
//		
//		for (ReportColumn reportColumn : reportConfiguration.getReportColumns()) {
//			if(!reportColumn.getIsVisible()) {
//				continue;
//			}
//			if (reportColumn.getReportGroupColumn().getDataType().equals(DataType.COLOR)) {
//				colorColumns.add(reportColumn);
//			}
//			switch (reportColumn.getReportGroupColumn().getProjectionType()) {
//			case PROPERTY:
//				projectionList.add(Projections.property(reportColumn.getReportGroupColumn().getProjectionName()));
//				break;
//			case GROUPPROPERTY:
//				projectionList.add(Projections.groupProperty(reportColumn.getReportGroupColumn().getProjectionName()));
//				break;
//			case COUNT:
//				projectionList.add(Projections.count(reportColumn.getReportGroupColumn().getProjectionName()));
//				break;
//			case COUNTDISTINCT:
//				projectionList.add(Projections.countDistinct(reportColumn.getReportGroupColumn().getProjectionName()));
//				break;
//			case SUM:
//				projectionList.add(Projections.sum(reportColumn.getReportGroupColumn().getProjectionName()));
//				break;
//			case AVERAGE:
//				projectionList.add(Projections.avg(reportColumn.getReportGroupColumn().getProjectionName()));
//				break;
//			case MIN:
//				projectionList.add(Projections.min(reportColumn.getReportGroupColumn().getProjectionName()));
//				break;
//			case MAX:
//				projectionList.add(Projections.max(reportColumn.getReportGroupColumn().getProjectionName()));
//				break;
//			case SQLPROJECTION:
//				String[] types = reportColumn.getReportGroupColumn().getSqlProjectionType().split(",");
//				Type[] sbtypes = new Type[types.length];
//				int count = 0;
//				
//				for (String type : types) {
//					switch (type) {
//					case "STRING":
//						sbtypes[count] = StandardBasicTypes.STRING;
//						break;
//					case "INTEGER":
//						sbtypes[count] = StandardBasicTypes.INTEGER;
//						break;
//					case "LONG":
//						sbtypes[count] = StandardBasicTypes.LONG;
//						break;
//					case "DOUBLE":
//						sbtypes[count] = StandardBasicTypes.DOUBLE;
//						break;		
//					}
//					count++;
//				}
//				projectionList.add(Projections.sqlGroupProjection(reportColumn.getReportGroupColumn().getSqlProjectionSql(),
//						reportColumn.getReportGroupColumn().getSqlProjectionGroup(), 
//						reportColumn.getReportGroupColumn().getSqlProjectionAlias().split(","), 
//						sbtypes));
//				break;
//			case SQLGROUPPROJECTION:
//				types = reportColumn.getReportGroupColumn().getSqlProjectionType().split(",");
//				sbtypes = new Type[types.length];
//				count = 0;
//				
//				for (String type : types) {
//					switch (type) {
//					case "STRING":
//						sbtypes[count] = StandardBasicTypes.STRING;
//						break;
//					case "INTEGER":
//						sbtypes[count] = StandardBasicTypes.INTEGER;
//						break;
//					case "LONG":
//						sbtypes[count] = StandardBasicTypes.LONG;
//						break;
//					case "DOUBLE":
//						sbtypes[count] = StandardBasicTypes.DOUBLE;
//						break;		
//					}
//					count++;
//				}
//				projectionList.add(Projections.sqlGroupProjection(reportColumn.getReportGroupColumn().getSqlProjectionSql(),
//						reportColumn.getReportGroupColumn().getSqlProjectionGroup(), 
//						reportColumn.getReportGroupColumn().getSqlProjectionAlias().split(","), 
//						sbtypes));
//				break;
//			}
//			
//			if (isSortAdding && isAscendingSort && sortColumn.equals(reportColumn.getReportGroupColumn().getColumnIdentity())) {
//				for (ReportColumnOrder reportColumnOrder : reportColumn.getReportColumnAscOrders()) {
//					criteria.addOrder(Order.asc(reportColumn.getReportGroupColumn().getProjectionName()));
//					isSortAdding = false;
////					if (reportColumnOrder.getIsAscending()) {
////						criteria.addOrder(Order.asc(reportColumn.getReportGroupColumn().getProjectionName()));
////						isSortAdding = false;
////					} else {
////						criteria.addOrder(Order.desc(reportColumn.getReportGroupColumn().getProjectionName()));
////						isSortAdding = false;
////					}
//				}
//			} else if (isSortAdding && !isAscendingSort && sortColumn.equals(reportColumn.
//					getReportGroupColumn().getColumnIdentity())) {
//				for (ReportColumnOrder reportColumnOrder : reportColumn.getReportColumnAscOrders()) {
//					criteria.addOrder(Order.desc(reportColumn.getReportGroupColumn().getProjectionName()));
//					isSortAdding = false;
////					if (reportColumnOrder.getIsAscending()) {
////						criteria.addOrder(Order.asc(reportColumn.getReportGroupColumn().getProjectionName()));
////						isSortAdding = false;
////					} else {
////						criteria.addOrder(Order.desc(reportColumn.getReportGroupColumn().getProjectionName()));
////						isSortAdding = false;
////					}
//				}
//			} else if (isSortAdding && reportColumn.getIsDefaultSortColumn()) {
//				defaultOrderColumn = reportColumn;
//				defaultColumnOrders.addAll(reportColumn.getReportColumnAscOrders());
//				
////				if (reportColumn.getIsDefaultSortColumn()) {
////					defaultColumnOrders.addAll(reportColumn.getReportColumnAscOrders());
////				} else {
////					defaultColumnOrders.addAll(reportColumn.getReportColumnAscOrders());
////				}
//			}
//		}
//		
//		if (isSortAdding) {
//			for (ReportColumnOrder reportColumnOrder : defaultColumnOrders) {
//				if (reportColumnOrder.getIsAscending()) {
//					criteria.addOrder(Order.asc(defaultOrderColumn.getReportGroupColumn().getProjectionName()));
//					isSortAdding = false;
//				} else {
//					criteria.addOrder(Order.desc(defaultOrderColumn.getReportGroupColumn().getProjectionName()));
//					isSortAdding = false;
//				}
//			}
//		}
//	}
//
//	protected boolean addingReportingHierarchy(Criteria criteria, DataSecType dataSecType, boolean withCreatedBy, long loggedInEmpId,
//			List<Long> reportingTreeIds, String poNoName) {
////		Create Alias to own for Owner, crb for createdBy, wfs for workflowStage
////		Add Submitted Document Number variable name for poNoName. Ex.- "poNoName";
//		
//		if (dataSecType.equals(DataSecType.SELF) && withCreatedBy) {
//			criteria.add(Restrictions.or(Restrictions.eq("own.employeeId", loggedInEmpId), Restrictions.eq("crb.employeeId", loggedInEmpId)));
//			
//		} else if (dataSecType.equals(DataSecType.SELF)) {
//			criteria.add(Restrictions.eq("own.employeeId", loggedInEmpId));
//			
//		} else if (dataSecType.equals(DataSecType.SUBORDINATES) && withCreatedBy) {
//			criteria.createAlias("own.empEmployment", "oep");
//			criteria.createAlias("oep.supervisor", "oes");
//			criteria.add(Restrictions.or(Restrictions.eq("own.employeeId", loggedInEmpId), Restrictions.eq("crb.employeeId", loggedInEmpId),
//					Restrictions.and(Restrictions.eq("oes.employeeId", loggedInEmpId), Restrictions.isNotNull(poNoName),
//							Restrictions.ne("wfs.workFlowStageId", 1L))));
//			
//		} else if (dataSecType.equals(DataSecType.SUBORDINATES)) {
//			criteria.createAlias("own.empEmployment", "oep");
//			criteria.createAlias("oep.supervisor", "oes");
//			criteria.add(Restrictions.or(Restrictions.eq("own.employeeId", loggedInEmpId), Restrictions.and(Restrictions.eq("oes.employeeId", loggedInEmpId),
//					Restrictions.isNotNull(poNoName), Restrictions.ne("wfs.workFlowStageId", 1L))));
//			
//		} else if (dataSecType.equals(DataSecType.REPORTING_TREE) && withCreatedBy) {
//			if (reportingTreeIds != null && !reportingTreeIds.isEmpty()) {
//				criteria.add(Restrictions.or(Restrictions.eq("own.employeeId", loggedInEmpId), Restrictions.eq("crb.employeeId", loggedInEmpId),
//						Restrictions.and(Restrictions.in("own.employeeId", reportingTreeIds),
//								Restrictions.isNotNull(poNoName), Restrictions.ne("wfs.workFlowStageId", 1L))));
//			} else {
//				criteria.add(Restrictions.or(Restrictions.eq("own.employeeId", loggedInEmpId), Restrictions.eq("crb.employeeId", loggedInEmpId)));
//			}
//			
//		} else if (dataSecType.equals(DataSecType.REPORTING_TREE)) {
//			if (reportingTreeIds != null && !reportingTreeIds.isEmpty()) {
//				criteria.add(Restrictions.or(Restrictions.eq("own.employeeId", loggedInEmpId),
//						Restrictions.and(Restrictions.in("own.employeeId", reportingTreeIds),
//								Restrictions.isNotNull(poNoName), Restrictions.ne("wfs.workFlowStageId", 1L))));
//			} else {
//				criteria.add(Restrictions.eq("own.employeeId", loggedInEmpId));
//			}
//			
//		} else if (dataSecType.equals(DataSecType.ALL_EMPLOYEES)) {
//			criteria.add(Restrictions.or(Restrictions.eq("own.employeeId", loggedInEmpId), Restrictions.eq("crb.employeeId", loggedInEmpId),
//					Restrictions.and(Restrictions.ne("own.employeeId", loggedInEmpId), Restrictions.isNotNull(poNoName),
//							Restrictions.ne("wfs.workFlowStageId", 1L))));
//		} else {
//			return false;
//		}
//		return true;
//	}
}

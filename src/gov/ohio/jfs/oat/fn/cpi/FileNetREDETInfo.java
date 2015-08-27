package gov.ohio.jfs.oat.fn.cpi;

import java.util.Iterator;

import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.core.Domain;
import com.filenet.api.core.IndependentlyPersistableObject;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;

public class FileNetREDETInfo implements FileNetAppInfo {

	@Override
	public void getTargetInfo(Domain dom, ObjectStore os, CPILog log) {
		
		PropertyFilter filters = new PropertyFilter();
		filters.addIncludeProperty(1, null, null, "DateCreated", null);
		filters.addIncludeProperty(1, null, null, "FIRST_NAME", null);
		filters.addIncludeProperty(1, null, null, "LAST_NAME", null);

		SearchSQL documentSql = new SearchSQL(
				"SELECT doc.[FIRST_NAME], doc.[LAST_NAME], doc.[DateCreated]"
						+ "  FROM [Document] doc" + " WHERE doc.[Id] = "
						+ log.getDocumentAccessed());
		SearchScope documentScope = new SearchScope(os);
		IndependentObjectSet documentSet = documentScope.fetchObjects(
				documentSql, null, filters, false);

		if (!documentSet.isEmpty()) {

			Iterator j = documentSet.iterator();
			IndependentlyPersistableObject doc = (IndependentlyPersistableObject) j
					.next();

			log.setPersonalId(doc.getProperties().getStringValue("FIRST_NAME")
					.trim()
					+ " "
					+ doc.getProperties().getStringValue("LAST_NAME").trim());
			log.setDateCreated(doc.getProperties().getDateTimeValue(
					"DateCreated"));
		}
	}
}

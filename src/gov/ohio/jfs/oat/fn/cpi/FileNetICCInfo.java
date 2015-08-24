package gov.ohio.jfs.oat.fn.cpi;

import java.util.Iterator;

import com.filenet.api.collection.IndependentObjectSet;
import com.filenet.api.core.Domain;
import com.filenet.api.core.IndependentlyPersistableObject;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;

public class FileNetICCInfo implements FileNetAppInfo {

	@Override
	public void getTargetInfo(Domain dom, ObjectStore os, CPILog log) {
		SearchSQL documentSql = new SearchSQL(
				"SELECT doc.[INDV_LAST_NM], doc.[OL_INQ_ID], doc.[DateCreated]"
						+ "  FROM [Document] doc" + " WHERE doc.[Id] = "
						+ log.getDocumentAccessed());
		SearchScope documentScope = new SearchScope(os);
		IndependentObjectSet documentSet = documentScope.fetchObjects(
				documentSql, null, null, false);

		if (!documentSet.isEmpty()) {

			Iterator j = documentSet.iterator();
			IndependentlyPersistableObject doc = (IndependentlyPersistableObject) j
					.next();

			log.setPersonalId(doc.getProperties().getStringValue("INDV_LAST_NM")
					.trim()
					+ "-"
					+ doc.getProperties().getStringValue("OL_INQ_ID").trim());
			log.setDateCreated(doc.getProperties().getDateTimeValue(
					"DateCreated"));
		}
	}
}

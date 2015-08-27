#!/bin/sh
############################################################################
#Define Application Name

############################################################################
# This command file launches the CPIExtract application.
# java -jar ExtractAuditLog.jar 
#           {OBJECTSTORE_NAME}
#           {APPLICATION_NAME}
#           {DOCUMENTCLASS_NAME}
#           {"APPEND" | "REPLACE"}
# example: -jar ExtractAuditLog.jar UC_DEV_OS FN_TRADE PETITION_DOC REPLACE
############################################################################
/opt/IBM/FileNet/ProcessEngine/_jvm/jre/bin/java -Djava.ext.dirs=/opt/IBM/FileNet/ProcessEngine/_jvm/jre/lib/ext:/opt/IBM/FileNet/ProcessEngine/CE_API/lib:/opt/IBM/FileNet/ProcessEngine/CE_API/wsi/lib -Dwasp.location=/opt/IBM/FileNet/ProcessEngine/CE_API/wsi -Djava.security.auth.login.config=/opt/IBM/FileNet/ProcessEngine/CE_API/config/jaas.conf.WSI -jar ExtractAuditLog.jar UC_DEV_OS FN_TRADE TRADE_ATAA_RTA_DOC REPLACE
/opt/IBM/FileNet/ProcessEngine/_jvm/jre/bin/java -Djava.ext.dirs=/opt/IBM/FileNet/ProcessEngine/_jvm/jre/lib/ext:/opt/IBM/FileNet/ProcessEngine/CE_API/lib:/opt/IBM/FileNet/ProcessEngine/CE_API/wsi/lib -Dwasp.location=/opt/IBM/FileNet/ProcessEngine/CE_API/wsi -Djava.security.auth.login.config=/opt/IBM/FileNet/ProcessEngine/CE_API/config/jaas.conf.WSI -jar ExtractAuditLog.jar UC_DEV_OS FN_TRADE TRADE_INV_DOC APPEND
/opt/IBM/FileNet/ProcessEngine/_jvm/jre/bin/java -Djava.ext.dirs=/opt/IBM/FileNet/ProcessEngine/_jvm/jre/lib/ext:/opt/IBM/FileNet/ProcessEngine/CE_API/wsi/lib -Dwasp.location=/opt/IBM/FileNet/ProcessEngine/CE_API/wsi -Djava.security.auth.login.config=/opt/IBM/FileNet/ProcessEngine/CE_API/config/jaas.conf.WSI -jar ExtractAuditLog.jar UC_DEV_OS FN_TRADE TRADE_TRA_DOC APPEND
/opt/IBM/FileNet/ProcessEngine/_jvm/jre/bin/java -Djava.ext.dirs=/opt/IBM/FileNet/ProcessEngine/_jvm/jre/lib/ext:/opt/IBM/FileNet/ProcessEngine/CE_API/wsi/lib -Dwasp.location=/opt/IBM/FileNet/ProcessEngine/CE_API/wsi -Djava.security.auth.login.config=/opt/IBM/FileNet/ProcessEngine/CE_API/config/jaas.conf.WSI -jar ExtractAuditLog.jar UC_DEV_OS FN_TRADE TRADE_TAA_DOC APPEND

############################################################################
# Once the extract file has been created, run the sql loader to upload the data.
# Data File Name: 
sqlldr direct=TRUE userid=AUDITCPILOG_LOAD/auditcpilog@OSD1 control=./CPILOG_STG_APPEND.ctl log=./FN_TRADE.log data=./FN_TRADE.csv

exit



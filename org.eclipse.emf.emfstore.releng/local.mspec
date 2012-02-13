<?xml version="1.0" encoding="UTF-8"?>
<mspec:mspec xmlns:mspec="http://www.eclipse.org/buckminster/MetaData-1.0" 
	installLocation="${user.home}\.hudson\jobs\emf-emfstore-integration\workspace\_target" 
	materializer="p2"
	name="local.mspec" url="build.cquery">
	
	<mspec:mspecNode namePattern="org\.eclipse\.emf\.emfstore\..*" materializer="workspace"/>
	
	<!--mspec:property key="resolve.target.platform" value="true"/-->
	<!--mspec:mspecNode materializer="workspace" filter="(buckminster.source=true)"/-->
</mspec:mspec>
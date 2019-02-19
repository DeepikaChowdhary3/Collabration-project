app.factory('JobService',function($http)
{
	/*alert('entering job service')*/
	var jobService={}
	
	jobService.addJob=function(job)
	{
		/*alert('entering addjob function in job service')*/
		var url="http://localhost:8085/middleware/addjob"
		return $http.post(url,job)
	}
	
	jobService.getAllJobs=function()
	{
		var url="http://localhost:8085/middleware/getalljobs"
		return $http.get(url)
	}
	return jobService;
})
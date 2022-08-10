module.exports={
    getfilmnvote:function (con,req,callback) {
        var query=`select f_nvote,f_rate from film where f_id='${req.body['f_id']}'`
        con.query(query,function (err,result,field) {
            if (err)
                throw err
            console.log(result)
            var callbck={
                fnvote:result[0].f_nvote,
                frate:result[0].f_rate
            }
            callback(callbck)

        })
    }
}
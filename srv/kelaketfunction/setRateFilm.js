module.exports={
    setratefilm:function (req,resp) {
        var connectdb=require("../basefunction/connectDb")
        var existenceuser=require("../basefunction/existenceUser")
        var getFilmNvote=require('../basefunction/getFilmNvote')
        var addScore=require("../basefunction/addSCore")
        var con=connectdb.con()
        con.connect()
        console.log("connected!!")
        existenceuser.existenceUser(con,req,function (returnvalue) {
            addScore.addscore(req,resp)
            if(returnvalue)
            {
                getFilmNvote.getfilmnvote(con,req,function (returnvalue2) {
                    console.log(returnvalue2)
                    var fvnote=returnvalue2['fnvote']
                    var frate=returnvalue2['frate']

                    var newnvote=fvnote+1
                    var newrate=(frate*fvnote+req.body['rate'])/newnvote
                    var query=`update film set f_nvote=${newnvote},f_rate=${newrate} where f_id=${req.body['f_id']}`
                    console.log(query)
                    con.query(query,function (err,result,field) {
                        if(err)
                            throw err
                        resp.setHeader('Content-Type', 'application/json');
                        resp.send({status:true})
                    })
                })
            }
            else{
                console.log("user not defined")
                resp.setHeader('Content-Type', 'application/json');
                resp.send({status:false})
            }

        })

    }
}
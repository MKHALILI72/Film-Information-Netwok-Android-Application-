module.exports={
    getListFilm:function (req,resp) {

        var connectdb=require("../basefunction/connectDb")
        var con=connectdb.con()

        if(req.body['email']!=undefined)
            var email=req.body['email']
        if(req.body['password']!=undefined)
            var password=req.body['password']
        // if(req.body['search']!=undefined)
        //     var search=req.body['search']
        // if(req.body['genre'])
        //     var genre=req.body['genre']
        // if(req.body['country'])
        //     var country=req.body['country']
        var checkuserlogin=require("../basefunction/checkUserLogin")
        if(req.body['country']=="ایرانی")
        {
            var country='ایران'
        }else
        {
            var country=''
        }
        var querysql=`select * from film where f_name like '%${req.body['search']}%' and f_genre like '%${req.body['genre']}%' and f_country like '%${country}%'`
           checkuserlogin.checkUserLogin(email,password,function (returnvalue) {

              if(returnvalue==true){
                     con.connect()
                     con.query(querysql,function (err,result,field) {
                         if (err)
                             throw err

                         var myresult=[]
                         for(var i=0;i<result.length;i++){
                             myresult[i]={
                                 status:true,
                                 id:result[i].f_id,
                                 name:result[i].f_name,
                                 poster:result[i].poster,
                                 director:result[i].f_director,
                                 createdate:result[i].f_createdate,
                                 rate:result[i].f_rate,
                                 nvote:result[i].f_nvote
                             }
                         }
                         console.log(myresult[0]);
                         resp.setHeader('Content-Type','application/json')
                         resp.send(myresult)
                     })
              }
              else{
                        resp.setHeader('Content-Type','application/json')
                        resp.send({status:false})
              }

          })

                     }
}
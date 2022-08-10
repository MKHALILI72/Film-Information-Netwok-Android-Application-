module.exports={
    getfilminfo:function (req,resp) {
        var connectdb=require("../basefunction/connectDb")
        var checkUserLogin=require("../basefunction/checkUserLogin")
        var isAddedFavorite=require("../basefunction/isAddedFavorite")
        var isFilmScored=require("../basefunction/isFilmScored")
        var con=connectdb.con()
        con.connect()
        console.log("connected!!!")

        checkUserLogin.checkUserLogin(req.body['email'],req.body['password'],function (returnvalue) {
            if(returnvalue)
            {
                isAddedFavorite.isaddedfavorite(req,resp,function (returnvalue2) {
                    isFilmScored.isfilmscored(req,resp,function (returnvalue3) {

                        var query=`select * from film where f_id=${req.body['f_id']}`
                        con.query(query,function (err,result,field) {
                            var respresult={
                                status:true,
                                f_id:result[0].f_id,
                                f_name:result[0].f_name,
                                poster:result[0].poster,
                                f_director:result[0].f_director,
                                f_createdate:result[0].f_createdate,
                                f_keyactor:result[0].f_keyactor,
                                f_summary:result[0].f_summary,
                                f_budget:result[0].f_budget,
                                f_genre:result[0].f_genre,
                                f_rate:result[0].f_rate,
                                f_nvote:result[0].f_nvote,
                                f_country:result[0].f_country,
                                f_addedfavorite:returnvalue2,
                                f_isfilmscored:returnvalue3
                            }

                            resp.setHeader('Content-Type', 'application/json');
                            resp.send(respresult)
                        })

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
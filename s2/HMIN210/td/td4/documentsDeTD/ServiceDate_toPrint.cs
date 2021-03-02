using System;
using System.Collections.Generic;
using System.Text;
using System.Web.Services;

namespace TDWS
{
    [WebService(Namespace = "http://mondomaine.fr/monAppli")]
    class ServiceDate {
        [WebMethod]
        public String getDate1(){
            ...
        }

        [WebMethod]
        public String getDate2(Format f){ 
	   ...
        }

        [WebMethod]
        public Date getDate3(){
            ...
        }
    }    

    public class Date{
       public int jour;
       public int mois;
       public int annee;        
       public Date(){
         }
        public Date(int j,int m,int a){
            jour=j;mois=m;annee=a;
        }
    }

    public enum Format
    {
        JMA, // jour mois annee
        HMS, // Heure, minutes, secondes
        HMSJMA
    }
}

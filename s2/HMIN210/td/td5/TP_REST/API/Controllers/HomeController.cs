using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using API.Models;

namespace API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class HomeController : ControllerBase
    {

        [HttpPost]
        public bool Authentification(LoginModel login)
        {
            if (login.Login.Equals("admin") && login.Password.Equals("admin")) return true;
            return false;
        }
    }
}

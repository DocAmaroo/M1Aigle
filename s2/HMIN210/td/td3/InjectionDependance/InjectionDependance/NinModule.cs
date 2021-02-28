using System;
using System.Collections.Generic;
using System.Text;
using Ninject.Modules;

namespace InjectionDependance
{
    class NinModule : NinjectModule
    {
        private bool flag;

        public NinModule(bool flag)
        {
            this.flag = flag;
        }

        public override void Load()
        {
            if (this.flag)
            {
                Bind<IChargementParam>().To<ChargementParam>();
            } else
            {
                Bind<IChargementParam>().To<ChargementParam2>();
            }
            
        }
    }
}

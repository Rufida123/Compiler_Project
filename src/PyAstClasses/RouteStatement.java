package PyAstClasses;

public class RouteStatement extends Statement {
        private RoutePath routePath;
        private RouteParams routeParams; // nullable
        private FuncDefStatement funcDef; // required

        public RoutePath getRoutePath() { return routePath; }
        public void setRoutePath(RoutePath routePath) { this.routePath = routePath; }

        public RouteParams getRouteParams() { return routeParams; }
        public void setRouteParams(RouteParams routeParams) { this.routeParams = routeParams; }

        public FuncDefStatement getFuncDef() { return funcDef; }
        public void setFuncDef(FuncDefStatement funcDef) { this.funcDef = funcDef; }

        @Override
        public String toString() {
            return "\nRouteStatement{" +
                    "\nroutePath=" + routePath +
                    ",\nrouteParams=" + routeParams +
                    ",\nfuncDef=" + funcDef +
                    "\n}";
        }


}

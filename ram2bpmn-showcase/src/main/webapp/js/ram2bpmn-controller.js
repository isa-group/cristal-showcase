function RamBpmnCtrl($scope, $http) {
    $scope.organization = {roles: []};
    $scope.templates = [];
    initProcessValues($scope);

    $http.get("/showcase/templates")
        .success(function (data) {
            $scope.templates = data;
        });


    $scope.loadBpmn = function () {
        $http.post("/showcase/bpmn", $scope.xmlBpmn)
            .success(function (data) {
                initProcessValues($scope);
                angular.forEach(data, function(activityName) {
                    $scope.process.activities.push({name: activityName})
                });
            });
    };

    $scope.transform = function () {
        $http.post("/showcase/transform",
                {bpmn: $scope.xmlBpmn,
                ram: {boundedRoles: $scope.ram},
                templ: $scope.templ
                }
            ).success(function (data) {
                $scope.output = data;
            })
    }

    $scope.validate = function () {
        $http.post("/showcase/validate",
                {bpmn: $scope.xmlBpmn,
                ram: {boundedRoles: $scope.ram},
                templ: $scope.templ
                }
            ).success(function (data) {
                $scope.invalid = data;
            })
    }

    $scope.isNotCompatible = function(activityName) {
        return $scope.invalid[activityName];
    }

    $scope.hasNotCompatible = function() {
        var notCompatible = false;
        for (activity in $scope.invalid) {
            if ($scope.invalid[activity]) {
                notCompatible = true;
                break;
            }
        }

        return notCompatible;
    }

}

function initProcessValues(scope) {
    scope.process = {activities: []};
    scope.ram = [];
    scope.templ = {};
    scope.invalid = {};
    scope.output = null;
}
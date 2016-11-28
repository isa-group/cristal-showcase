/*
 * RASCICtrl - Controller for the RASCI matrix editor
 */

function RASCICtrl($scope, $log) {
    this.scope = $scope;
    this.log = $log;
    this.rasciRoles = {
      "responsible": "R",
      "accountable": "A",
      "support": "S",
      "consulted": "C",
      "informed": "I"
    };
    this.rasciCell = null;
    this.addRole = null;
}

RASCICtrl.prototype.loadCell = function(data) {
    var cell = {
        role: data.role,
        activity: data.activity
    };

    angular.forEach(this.rasciRoles, function(abbr, role) {
        cell[role] = {enabled: false};
    });

    angular.forEach(data.assignment, function(boundRole) {
        if (boundRole.activity.name == data.activity && boundRole.role.name == data.role) {
            cell[boundRole.responsibility.rasci] = {enabled: true, context: boundRole.organisationalContext, restrictions: boundRole.additionalRestrictions};
        }
    });

    this.rasciCell = cell;
};

RASCICtrl.prototype.saveCell = function(ram) {
    var cell = this.rasciCell;
    var i = ram.length;

    while(i--) {
        if (ram[i].role.name == cell.role && ram[i].activity.name == cell.activity) {
           ram.splice(i,1);
        }
    }

    angular.forEach(this.rasciRoles, function(abbr, rasciRole) {
        if (cell[rasciRole].enabled) {
            ram.push({
                role: {name: cell.role},
                activity: {name: cell.activity},
                responsibility: {rasci: rasciRole},
                organisationalContext: cell[rasciRole].context,
                additionalRestrictions: cell[rasciRole].restrictions
            });
        }
    });

    this.rasciCell = null;
    this.scope.validate();
};

RASCICtrl.prototype.cancelDetails = function() {
    this.rasciCell = null;
};

RASCICtrl.prototype.showDetails = function() {
    return this.rasciCell;
};

RASCICtrl.prototype.depict = function(data) {
    var result = [];
    var rasciRoles = this.rasciRoles;

    angular.forEach(data.assignment, function(boundRole) {
        if (boundRole.role.name == data.role && boundRole.activity.name == data.activity) {
            var content = rasciRoles[boundRole.responsibility.rasci];
            if (boundRole.organisationalContext || boundRole.additionalRestrictions) {
                content += "*";
            }
            result.push(content);
        }
    });

    return result.join(" / ");
};

RASCICtrl.prototype.newRole = function(roles) {
    roles.push({name: this.addRole});
    this.addRole = "";
}

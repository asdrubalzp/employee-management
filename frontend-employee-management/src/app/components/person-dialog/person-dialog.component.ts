import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PersonModel } from 'src/app/models/person.model';
import { PersonService } from 'src/app/services/persons.service';
import { OnInit } from '@angular/core';
@Component({
  selector: 'app-person-dialog',
  templateUrl: './person-dialog.component.html',
  styleUrls: ['./person-dialog.component.css'],
})
export class PersonDialogComponent implements OnInit {
  @Input() personModel!: PersonModel;
  @Input() displayDialog!: boolean;
  @Input() isEditing!: boolean;
  @Output() displayDialogChange = new EventEmitter<boolean>();
  @Output() personAdded = new EventEmitter<PersonModel>();
  @Output() personEdited = new EventEmitter<PersonModel>();
  submitted: boolean = false;
  minDate = new Date(1900, 0, 1);
  maxDate = new Date(new Date().getFullYear() - 18, 11, 31);

  constructor(private personService: PersonService) {}

  ngOnInit() {}
  hideDialog() {
    this.displayDialogChange.emit(false);
    this.isEditing = false;
    this.submitted = false;
  }
  isFormValid(): boolean {
    return (
      this.personModel.name != null &&
      this.personModel.lastName != null &&
      this.isValidEmail(this.personModel.email) &&
      this.personModel.salary != null &&
      this.personModel.birthDate != null
    );
  }

  savePerson() {
    if (this.isEditing) {
      this.personService.updatePerson(this.personModel).subscribe((person) => {
        this.personEdited.emit(this.personModel);
        this.hideDialog();
        this.submitted = true;
      });
      return;
    }

    this.personService.addPerson(this.personModel).subscribe((person) => {
      this.personAdded.emit(this.personModel);
      this.hideDialog();
      this.submitted = true;
    });
  }

  isValidEmail(email: string): boolean {
    return /\S+@\S+\.\S+/.test(email);
  }
}

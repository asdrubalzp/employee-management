import { Component, OnInit } from '@angular/core';
import { PersonModel } from 'src/app/models/person.model';
import { PersonService } from 'src/app/services/persons.service';

@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styleUrls: ['./person.component.css'],
})
export class PersonComponent implements OnInit {
  persons: PersonModel[] = [];
  selectedPerson!: PersonModel;
  personModel: PersonModel = this.personService.getEmptyPerson();
  isEditing: boolean = false;
  Delete: string = 'Delete';

  personDialog: boolean = false;

  seachText: string = '';
  constructor(private personService: PersonService) {}

  ngOnInit(): void {
    this.getPersons();
  }

  getPersons() {
    this.personService.getPersons().subscribe((persons) => {
      this.persons = persons;
    });
  }

  openNew() {
    this.isEditing = false;
    this.personModel = this.personService.getEmptyPerson();
    this.personDialog = true;
  }

  editProduct(person: PersonModel) {
    console.log('editing person', person);
    this.personModel = { ...person };
    this.isEditing = true;
    this.personDialog = true;
  }

  deleteProduct(id: number) {
    this.personService.deletePerson(id).subscribe(() => {
      this.getPersons();
    });
  }

  deleteSelectedPerson() {
    console.log('delete selected persons');
  }

  handlePersonAdded(person: PersonModel) {
    this.persons.push(person);
  }

  handlePersonEdited(person: PersonModel) {
    const index = this.persons.findIndex((p) => p.id === person.id);
    this.persons[index] = person;
  }
}

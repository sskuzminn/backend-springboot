package ru.kuzminss.tasklist.backendspringboot.search;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
//Возможные значения, по которым можно искать категории
public class CategorySearchValues {

    private String text;


}

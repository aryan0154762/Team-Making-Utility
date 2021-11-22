#!/bin/sh


login() {
    choice=$(echo -e "1. Add Friend\n2. Add Foe\n3. Remove Friend\n4. Remove Foe\n5. See Profile\n6. Exit\n" | fzf)

    case $choice in
        "1. Add Friend") clear; echo -n "Enter Password : "; read -s pass
            javac DatabaseInterface.java && java -cp "sqlite-jdbc-3.36.0.3.jar:" DatabaseInterface 2 $1 $pass friend; sleep 2 ;;
        "2. Add Foe") clear; echo -n "Enter Password : "; read -s pass
            javac DatabaseInterface.java && java -cp "sqlite-jdbc-3.36.0.3.jar:" DatabaseInterface 2 $1 $pass foe; sleep 2 ;;
        "2. Remove Friend")  ;;
        "2. Remove Foe")  ;;
        "5. See Profile") javac MemberInterface.java && java -cp "sqlite-jdbc-3.36.0.3.jar:" MemberInterface $1 | fzf ;;
        "6. Exit")  ;;
    esac
}


while [ true ]
do
    userid=$(echo -e "1. Suggest Groups\n2. Member Login\n3. Display all Members\n4. Display all Relations\n5. Add Member\n6. Exit\n" | fzf)

    case $userid in
        "1. Suggest Groups") clear; javac DatabaseInterface.java && java -cp "sqlite-jdbc-3.36.0.3.jar:" DatabaseInterface 1 ;;
        "2. Member Login") echo -n "Enter your User ID : "; read uid; login $uid ;;
        "3. Display all Members") javac DatabaseInterface.java && java -cp "sqlite-jdbc-3.36.0.3.jar:" DatabaseInterface 3 | fzf ;;
        "4. Display all Relations") javac DatabaseInterface.java && java -cp "sqlite-jdbc-3.36.0.3.jar:" DatabaseInterface 4 | fzf ;;
        "5. Add Member") javac DatabaseInterface.java && java -cp "sqlite-jdbc-3.36.0.3.jar:" DatabaseInterface 5 ;;
        "6. Exit") break ;;
    esac
done

'Get Java path.
Dim path, javaPath, jarPath, command
Set shell = WScript.CreateObject("WScript.Shell")

' Проверка JAVA_HOME
path = shell.Environment.Item("JAVA_HOME")
If path = "" Then
    MsgBox "ОШИБКА: Не найден JAVA_HOME!" & vbCrLf & vbCrLf & "Установите Java и настройте переменную JAVA_HOME", vbOKOnly, "Database Installer"
    WScript.Quit 1
End If

' Формирование пути к Java
If InStr(path, "\bin") = 0 Then
    javaPath = path + "\bin\java.exe"
Else
    javaPath = path + "\java.exe"
End If

' Проверка существования java.exe
Set fso = CreateObject("Scripting.FileSystemObject")
If Not fso.FileExists(javaPath) Then
    MsgBox "ОШИБКА: Java не найден по пути:" & vbCrLf & javaPath, vbOKOnly, "Database Installer"
    WScript.Quit 1
End If

' Проверка JAR файла
jarPath = fso.GetParentFolderName(WScript.ScriptFullName) + "\Database_Installer_GS.jar"
If Not fso.FileExists(jarPath) Then
    MsgBox "ОШИБКА: JAR файл не найден:" & vbCrLf & jarPath, vbOKOnly, "Database Installer"
    WScript.Quit 1
End If

' Формирование команды
command = """" & javaPath & """ -jar """ & jarPath & """"

' Показываем информацию
MsgBox "Запуск Database Installer:" & vbCrLf & vbCrLf & "Java: " & javaPath & vbCrLf & "JAR: " & jarPath, vbOKOnly, "Database Installer"

' Запуск установщика
Dim exitCode
exitCode = shell.Run(command, 1, True)

If exitCode <> 0 Then
    MsgBox "ОШИБКА: Database Installer завершился с кодом: " & exitCode, vbOKOnly, "Database Installer"
Else
    MsgBox "Database Installer завершен успешно!", vbOKOnly, "Database Installer"
End If

package cl.altair.accesos.wizard;

import org.eclipse.jface.wizard.Wizard;


public class ElWizard extends Wizard {

  protected PaginaUno one;
  protected PaginaDos two;

  public ElWizard() {
    super();
    setNeedsProgressMonitor(true);
  }

  @Override
  public void addPages() {
    one = new PaginaUno();
    two = new PaginaDos();
    addPage(one);
    addPage(two);
  }

  @Override
  public boolean performFinish() {
    // Print the result to the console
    System.out.println(one.getText1());
    System.out.println(two.getText1());

    return true;
  }
} 
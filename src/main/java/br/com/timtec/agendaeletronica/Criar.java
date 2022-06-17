package br.com.timtec.agendaeletronica;

import java.sql.Connection;
import java.util.Arrays;

import org.apache.wicket.feedback.ErrorLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;

import br.com.timtec.agendaeletronica.contato.Contato;
import br.com.timtec.agendaeletronica.contato.ContatoDAO;
import br.com.timtec.agendaeletronica.contato.EstadoCivil;

public class Criar extends BasePage{
	private static final long serialVersionUID = -6619311231415722854L;
	
	public Criar(){
		add(new Label("titulo", "Criação de Contato"));
		
		Contato contato = new Contato();
		CompoundPropertyModel<Contato> compoundPropertyModelContrato = new CompoundPropertyModel<Contato>(contato);
		Form<Contato> form = new Form<Contato>("formularioContato", compoundPropertyModelContrato){
			private static final long serialVersionUID = -1620862938532623698L;
			
			@Override
			public void onSubmit(){
				Contato contatoSubmetido = getModelObject();
				salvar(contatoSubmetido);
				setResponsePage(Inicio.class);
			}
			
		};
		add(form);
		
		TextField<String> inputNome = new TextField<String>("nome");
		TextField<String> inputEmail = new TextField<String>("email");
		TextField<String> inputTelefone = new TextField<String>("telefone");
		DropDownChoice<EstadoCivil> comboEstadoCivil = new DropDownChoice<EstadoCivil>("estadoCivil",
				Arrays.asList(EstadoCivil.values()), new IChoiceRenderer<EstadoCivil>() {
					private static final long serialVersionUID = 1L;

					@Override
					public Object getDisplayValue(EstadoCivil arg0) {
						// Valor que será exibido na interface para o usuário correspondente a aquele valor do dropDownChoice
						return arg0.getLabel();
					}

					@Override
					public String getIdValue(EstadoCivil arg0, int arg1) {
						// Valor que será colocado no option do HTML para diferenciar uma opção da outra
						return arg0.name();
					}
				});
		form.add(inputNome, inputEmail, inputTelefone, comboEstadoCivil);
		
		inputNome.setLabel(Model.of("Nome do contato")).setRequired(true).add(StringValidator.maximumLength(10));
		inputEmail.setLabel(Model.of("Email do contato")).add(EmailAddressValidator.getInstance());
		
		add(new FeedbackPanel("feedbackMessage", new ErrorLevelFeedbackMessageFilter(FeedbackMessage.ERROR)));
	}
	
	private void salvar(Contato contatoSubmetido) {
//		System.out.println("Contato a inserir: " + contatoSubmetido);
		Connection conexao = ((WicketApplication) getApplication()).getConexao();
		ContatoDAO dao = new ContatoDAO(conexao);
		dao.inserir(contatoSubmetido);
	}

}
